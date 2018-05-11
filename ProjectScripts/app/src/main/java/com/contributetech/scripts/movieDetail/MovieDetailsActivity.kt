package com.contributetech.scripts.movieDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
import com.contributetech.scripts.R
import com.contributetech.scripts.application.ScriptsApplication
import com.contributetech.scripts.commonListeners.IMovieClick
import com.contributetech.scripts.database.Genre
import com.contributetech.scripts.database.ProductionCompany
import com.contributetech.scripts.database.movieDetails.MovieDetail
import com.contributetech.scripts.database.movieDetails.MovieDetailsDao
import com.contributetech.scripts.network.NetworkImageUtil
import com.contributetech.scripts.network.ParamsUtil
import com.contributetech.scripts.network.TMDBApi
import com.contributetech.scripts.network.responseVo.CollectionResponseVO
import com.contributetech.scripts.util.ImageUtil
import com.facebook.drawee.view.SimpleDraweeView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsActivity:AppCompatActivity(), IMovieClick {

    var movieId:Int = -1

    @Inject
    lateinit var mMoviesDao:MovieDetailsDao

    @Inject
    lateinit var mTMDBApi:TMDBApi

    lateinit var vpCarousel:ViewPager
    lateinit var imageCarouselAdapter:PagerImageCarouselAdapter
    lateinit var rvCollection:RecyclerView
    lateinit var adapterCollection:CollectionItemRecyclerAdapter


    lateinit var sdvPoster:SimpleDraweeView
    lateinit var tvTitle:TextView
    lateinit var tvRating:TextView
    lateinit var tvOverview:TextView
    lateinit var tvRevenue:TextView
    lateinit var tvReleaseDate:TextView
    lateinit var tvDuration:TextView
    lateinit var tvGenre:TextView
    lateinit var tvProductions:TextView

    private var mDisposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        (application as ScriptsApplication).mAppComponent.inject((this))
        val intent:Intent = intent
        if(intent.hasExtra("id")) {
            movieId = intent.getIntExtra("id", -1)
        }
        initView()
        if(movieId != -1) {
            fetchMovie()
        }
    }

    private fun initView() {
        vpCarousel = findViewById(R.id.vp_movie_carousel) as ViewPager
        imageCarouselAdapter = PagerImageCarouselAdapter(this)
        rvCollection = findViewById(R.id.rv_collection) as RecyclerView
        rvCollection.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapterCollection = CollectionItemRecyclerAdapter(this)
        adapterCollection.onClickListener = this
        rvCollection.adapter = adapterCollection


        vpCarousel.adapter = imageCarouselAdapter
        sdvPoster = findViewById(R.id.sdv_poster_image)
        tvTitle = findViewById(R.id.tv_title)
        tvOverview = findViewById(R.id.tv_overview)
        tvRating = findViewById(R.id.tv_rating)
        tvRevenue= findViewById(R.id.tv_revenue)
        tvReleaseDate = findViewById(R.id.tv_release_date)
        tvDuration = findViewById(R.id.tv_duration)
        tvGenre = findViewById(R.id.tv_genre)
        tvProductions= findViewById(R.id.tv_production)
    }

    private fun fetchMovie() {
        val disposable = mTMDBApi.getMovieDetail(movieId, ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setMovieDetails(it)
                }, {
                    handleError(it)
                })
        mDisposables.add(disposable)
    }

    private fun fetchCollection(collectionId:Int) {
        val disposable = mTMDBApi.getCollectionDetail(collectionId, ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setData(it)
                }, {
                    handleError(it)
                })
        mDisposables.add(disposable)
    }

    private fun handleError(throwable: Throwable) {

    }

    private fun setData(response: CollectionResponseVO) {
        adapterCollection.setData(response.parts)
    }

    private fun setMovieDetails(movie: MovieDetail) {
        setPosterImage(movie.posterPath)
        if(movie.title != null) {
            tvTitle.setText(movie.title)
        }
        if(movie.voteAvg != null) {
            tvRating.setText(movie.voteAvg.toString())
        }
        getCarouselImages(movie)
        tvOverview.setText(movie.overview)
        tvReleaseDate.setText(movie.releaseDate)

        if(movie.revenue > 0)
            tvRevenue.setText("$ " + movie.revenue)
        else
            tvRevenue.setText("-")

        if (movie.runtime > 0)
            setMovieDuration(movie.runtime)
        else
            tvDuration.setText("-")

        if (movie.genres != null)
            setGenre(movie.genres)
        if (movie.productionCompanies != null)
            setProduction(movie.productionCompanies)

        if(movie.collection != null && movie.collection!!.id > 0)
            fetchCollection(movie.collection!!.id)
    }

    private fun setMovieDuration(runtime: Int) {
        var duration:Int = runtime
        var durationString = ""
        durationString = (duration % 60).toString() + " mins"
        duration = duration / 60
        if(duration > 0)
            durationString = (duration).toString() + " hr  " + durationString
        tvDuration.setText(durationString)
    }

    private fun setProduction(productionCompanies: ArrayList<ProductionCompany>?) {
        var productions = ""
        if (productionCompanies != null) {
            for (company: ProductionCompany in productionCompanies) {
                productions = productions + company.name + ","
            }
        }
        if(productions.length > 0){
            productions  = productions.substring(0, productions.length-1)
        }
        tvProductions.setText(productions)
    }

    private fun setGenre(genreList: ArrayList<Genre>) {
        var genres = ""
        for (genre: Genre in genreList) {
            genres = genres + genre.name + ","
        }
        if(genres.length > 0){
            genres  = genres.substring(0, genres.length-1)
        }
        tvGenre.setText(genres)
    }

    private fun getCarouselImages(movie:MovieDetail) {
        val mImageList:MutableList<String> = mutableListOf()
        val url:String? = movie.backdropPath
        if(url != null) {
            mImageList.add(url)
        }
        imageCarouselAdapter.setData(mImageList as ArrayList<String>)
    }

    private fun setPosterImage(imageUrl:String?) {
        if (imageUrl != null) {
            val path: String = NetworkImageUtil.getImagePath(imageUrl, ImageUtil.LandscapeSizes.large_size)
            val uri = Uri.parse(path)
            sdvPoster.setImageURI(uri.toString())
        }
    }

    override fun onMovieClick(id: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("id", id);
        startActivity(intent)
    }
}