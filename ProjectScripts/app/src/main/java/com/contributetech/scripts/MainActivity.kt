package com.contributetech.scripts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.TextView
import com.contributetech.scripts.application.ScriptsApplication
import com.contributetech.scripts.database.MovieDetail
import com.contributetech.scripts.database.MovieDetailDao
import com.contributetech.scripts.homescreen.CarouselPagerAdapter
import com.contributetech.scripts.network.responseVo.NowShowingResponseVO
import com.contributetech.scripts.network.ParamsUtil
import com.contributetech.scripts.network.TMDBApi
import com.contributetech.scripts.network.responseVo.PopularMoviesResponseVO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var api:TMDBApi

    @Inject
    lateinit var mMovieDetailsDao: MovieDetailDao
    var nowShowingMovieList:ArrayList<Int> = arrayListOf()
    var upcomingMovieList:ArrayList<Int> = arrayListOf()
    var topRatedMovieList:ArrayList<Int> = arrayListOf()
    var popularMovieList:ArrayList<Int> = arrayListOf()

    lateinit var vpCarousel:ViewPager
    lateinit var pagerAdapter:CarouselPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as ScriptsApplication).mAppComponent.inject((this))
        initView()
        fetchMovies()
        subscribeToNowPlaying()
    }

    private fun initView() {
        vpCarousel = findViewById(R.id.vp_carousel)
        pagerAdapter = CarouselPagerAdapter(this)
        vpCarousel.adapter = pagerAdapter
    }

    private fun fetchMovies() {
        fetchNowShowingMoviesResults()
        fetchPopularMoviesResults()
        fetchUpcomingMoviesResults()
        fetchTopRatedMoviesResults()
    }

    private fun subscribeToNowPlaying() {
        mMovieDetailsDao.getMoviesByIds(nowShowingMovieList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setNowPlayingList, this::handleError)

    }

    private fun setNowPlayingList(movieList:List<MovieDetail>) {
        if(!movieList.isEmpty())
            pagerAdapter.setData(movieList as ArrayList<MovieDetail>)
    }

    private fun fetchNowShowingMoviesResults() {
        api.getNowPlayingMovies(ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleNowShowingResults, this::handleError)
    }

    private fun fetchPopularMoviesResults() {
        api.getPopularMovies(ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handlePopularMoviesResults, this::handleError)
    }

    private fun fetchUpcomingMoviesResults() {
        api.getUpcomingMovies(ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleUpcomingMoviesResults, this::handleError)
    }

    private fun fetchTopRatedMoviesResults() {
        api.getTopRatedMovies(ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleTopRatedMoviesResults, this::handleError)
    }

    private fun handleError(error:Throwable) {
    }

    fun handleNowShowingResults(nowShowingResponse: NowShowingResponseVO) {
        val moviesList : List<MovieDetail> = nowShowingResponse.results;
        nowShowingMovieList = storeMoviesForNowPlaying(moviesList);
    }

    private fun storeMoviesForNowPlaying(moviesList: List<MovieDetail>): ArrayList<Int> {
        val newList:ArrayList<Int> = nowShowingMovieList;
        for(movie:MovieDetail in moviesList) {
            if(!newList.contains(movie.id)) {
                newList.add(movie.id)
            }
        }
        val thread = Thread({
            mMovieDetailsDao.insertMoviesList(moviesList)
            runOnUiThread(){
                subscribeToNowPlaying()
            }
        })
        thread.start()
        return newList;
    }

    private fun storeMoviesForUpcoming(moviesList: List<MovieDetail>): ArrayList<Int> {
        val newList:ArrayList<Int> = upcomingMovieList;
        for(movie:MovieDetail in moviesList) {
            if(!newList.contains(movie.id)) {
                newList.add(movie.id)
            }
        }
        val thread = Thread({
            mMovieDetailsDao.insertMoviesList(moviesList)
        })
        thread.start()
        return newList;
    }

    private fun storeMoviesForTopRated(moviesList: List<MovieDetail>): ArrayList<Int> {
        val newList:ArrayList<Int> = topRatedMovieList;
        for(movie:MovieDetail in moviesList) {
            if(!newList.contains(movie.id)) {
                newList.add(movie.id)
            }
        }
        val thread = Thread({
            mMovieDetailsDao.insertMoviesList(moviesList)
        })
        thread.start()
        return newList;
    }

    private fun storeMoviesForPopular(moviesList: List<MovieDetail>): ArrayList<Int> {
        val newList:ArrayList<Int> = popularMovieList;
        for(movie:MovieDetail in moviesList) {
            if(!newList.contains(movie.id)) {
                newList.add(movie.id)
            }
        }
        val thread = Thread({
            mMovieDetailsDao.insertMoviesList(moviesList)
        })
        thread.start()
        return newList;
    }

    fun handlePopularMoviesResults(popularMoviesResult: PopularMoviesResponseVO) {
        val moviesList : List<MovieDetail> = popularMoviesResult.results;
        popularMovieList = storeMoviesForPopular(moviesList);
    }

    fun handleUpcomingMoviesResults(popularMoviesResult: NowShowingResponseVO) {
        val moviesList : List<MovieDetail> = popularMoviesResult.results;
        upcomingMovieList = storeMoviesForUpcoming(moviesList);
    }

    fun handleTopRatedMoviesResults(popularMoviesResult: PopularMoviesResponseVO) {
        val moviesList : List<MovieDetail> = popularMoviesResult.results;
        topRatedMovieList = storeMoviesForTopRated(moviesList);
    }
}


