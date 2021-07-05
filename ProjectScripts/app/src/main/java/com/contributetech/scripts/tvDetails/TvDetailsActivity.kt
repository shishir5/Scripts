package com.contributetech.scripts.tvDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.contributetech.scripts.R
import com.contributetech.scripts.application.ScriptsApplication
import com.contributetech.scripts.commonListeners.ITvClick
import com.contributetech.scripts.database.*
import com.contributetech.scripts.database.tvDetails.SeasonsItemDetail
import com.contributetech.scripts.database.tvDetails.TvDetail
import com.contributetech.scripts.database.tvListItemDetail.TvShowListItem
import com.contributetech.scripts.homescreen.tvFragment.HorizontalTvListRecyclerAdapter
import com.contributetech.scripts.movieDetail.*
import com.contributetech.scripts.network.NetworkImageUtil
import com.contributetech.scripts.network.ParamsUtil
import com.contributetech.scripts.network.TMDBApi
import com.contributetech.scripts.network.responseVo.*
import com.contributetech.scripts.util.ImageUtil
import com.facebook.drawee.view.SimpleDraweeView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TvDetailsActivity:AppCompatActivity(), ITvClick, IVideoThumbnailClick {

    var tvId:Int = -1

    @Inject
    lateinit var mTMDBApi: TMDBApi

    lateinit var vpCarousel: ViewPager
    lateinit var videoCarouselAdapter: PagerVideoCarouselAdapter
    lateinit var rvCollection: RecyclerView
    lateinit var rvCast: RecyclerView
    lateinit var vpReview: ViewPager
    lateinit var rvSimilar: RecyclerView
    lateinit var adapterCollection: SeasonsItemRecyclerAdapter
    lateinit var adapterReview: ReviewsPagerAdapter
    lateinit var adapterCast: CastHorizontalRecyclerAdapter
    lateinit var adapterSimilar: HorizontalTvListRecyclerAdapter


    lateinit var sdvPoster: SimpleDraweeView
    lateinit var tvTitle: TextView
    lateinit var tvRating: TextView
    lateinit var tvOverview: TextView
    lateinit var tvRevenue: TextView
    lateinit var tvReleaseDate: TextView
    lateinit var tvDuration: TextView
    lateinit var tvGenre: TextView
    lateinit var tvProductions: TextView

    private var mDisposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        (application as ScriptsApplication).mAppComponent.inject((this))
        val intent: Intent = intent
        if(intent.hasExtra("id")) {
            tvId = intent.getIntExtra("id", -1)
        }
        initView()
        if(tvId != -1) {
            fetchTv()
            fetchVideos()
            fetchReviews()
            fetchCredits()
            fetchSimilarTv()
        }
    }

    private fun fetchVideos() {
        val disposable = mTMDBApi.getVideosForTv(tvId, ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setMovieVideos(it)
                }, {
                    handleError(it)
                })
        mDisposables.add(disposable)    }

    private fun setMovieVideos(videoListResponse: VideoListResponseVO) {
        val videoList:ArrayList<VideoListItem> =  videoListResponse.results
        if(videoCarouselAdapter == null) {
            videoCarouselAdapter = PagerVideoCarouselAdapter(this, videoList, this)
            vpCarousel.adapter = videoCarouselAdapter
        }
        else {
            videoCarouselAdapter.setData(videoList)
        }
        vpCarousel.setCurrentItem(0)
    }

    private fun initView() {
        vpCarousel = findViewById(R.id.vp_movie_carousel) as ViewPager
        videoCarouselAdapter = PagerVideoCarouselAdapter(this, arrayListOf<VideoListItem>(), this)
        rvCollection = findViewById(R.id.rv_collection) as RecyclerView
        vpReview = findViewById(R.id.vp_reviews) as ViewPager
        rvCast = findViewById(R.id.rv_casts) as RecyclerView
        rvSimilar = findViewById(R.id.rv_similar) as RecyclerView
        rvCollection.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvSimilar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapterCollection = SeasonsItemRecyclerAdapter()
        adapterCollection.itemClickListener = this
        adapterSimilar = HorizontalTvListRecyclerAdapter()
        adapterSimilar.listener = this

        adapterCast = CastHorizontalRecyclerAdapter()
        adapterReview = ReviewsPagerAdapter(this)

        rvCollection.adapter = adapterCollection
        rvCast.adapter = adapterCast
        vpReview.adapter = adapterReview
        vpCarousel.adapter = videoCarouselAdapter
        rvSimilar.adapter = adapterSimilar

        rvCollection.isNestedScrollingEnabled = false
        rvCast.isNestedScrollingEnabled = false
        rvSimilar.isNestedScrollingEnabled = false


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

    private fun fetchTv() {
        val disposable = mTMDBApi.getTvDetail(tvId, ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setTvDetails(it)
                }, {
                    handleError(it)
                })
        mDisposables.add(disposable)
    }

    private fun fetchCredits() {
        val disposable = mTMDBApi.getCreditsForTv(tvId, ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setCredits(it)
                }, {
                    handleError(it)
                })
        mDisposables.add(disposable)
    }

    private fun fetchReviews() {
        val disposable = mTMDBApi.getTvReviews(tvId, ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setReviews(it)
                }, {
                    handleError(it)
                })
        mDisposables.add(disposable)
    }

    private fun fetchSimilarTv() {
        val disposable = mTMDBApi.getSimilarTv(tvId, ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setSimilarMovies(it)
                }, {
                    handleError(it)
                })
        mDisposables.add(disposable)
    }

    private fun setSimilarMovies(similarTvResponse: SimilarTvResponseVO) {
        adapterSimilar.setData(similarTvResponse.results as ArrayList<TvShowListItem>)
    }

    private fun setReviews(reviewResponse: ReviewResponseVO) {
        val reviews:List<Review> = reviewResponse.results
        adapterReview.setData(reviews as ArrayList<Review>)
    }

    private fun setCredits(creditsResponse: CreditResponseVO) {
        val casts:ArrayList<Cast> = creditsResponse.cast
        adapterCast.setData(casts)
    }

    private fun handleError(throwable: Throwable) {

    }

    private fun setTvDetails(tv: TvDetail) {
        setPosterImage(tv.posterPath)
        if(tv.originalName != null) {
            tvTitle.setText(tv.originalName)
        }
        if(tv.voteAvg != null) {
            tvRating.setText(tv.voteAvg.toString())
        }
        tvOverview.setText(tv.overview)
        tvReleaseDate.setText(tv.firstAirDate)

        tvRevenue.visibility = View.GONE
        tvDuration.visibility = View.GONE



        if (tv.genres != null)
            setGenre(tv.genres)
        if (tv.productionCompaies != null)
            setProduction(tv.productionCompaies)

        setSeasons(tv.seasons)
    }

    private fun setSeasons(seasons: ArrayList<SeasonsItemDetail>) {
        adapterCollection.setData(seasons)

    }


    private fun setProduction(productionCompanies: ArrayList<ProductionCompany>?) {
        var productions = ""
        if (productionCompanies != null) {
            for (company: ProductionCompany in productionCompanies) {
                productions = productions + company.name + ", "
            }
        }
        if(productions.length > 0){
            productions  = productions.substring(0, productions.length-2)
        }
        tvProductions.setText(productions)
    }

    private fun setGenre(genreList: ArrayList<Genre>) {
        var genres = ""
        for (genre: Genre in genreList) {
            genres = genres + genre.name + ", "
        }
        if(genres.length > 0){
            genres  = genres.substring(0, genres.length-2)
        }
        tvGenre.setText(genres)
    }

    private fun setPosterImage(imageUrl:String?) {
        if (imageUrl != null) {
            val path: String = NetworkImageUtil.getImagePath(imageUrl, ImageUtil.LandscapeSizes.large_size)
            val uri = Uri.parse(path)
            sdvPoster.setImageURI(uri.toString())
        }
    }

    override fun onTvClick(id: Int) {
        val intent = Intent(this, TvDetailsActivity::class.java)
        intent.putExtra("id", id);
        startActivity(intent)
    }

    override fun onSeasonClick(id: Int) {
    }

    override fun onClick(videoUrl: String) {
        var intent = Intent(this, FullScreenVideoActivity::class.java)
        intent.putExtra("video_url", videoUrl);
        startActivity(intent)
    }

}