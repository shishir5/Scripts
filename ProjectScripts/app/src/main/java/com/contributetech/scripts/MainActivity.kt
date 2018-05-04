package com.contributetech.scripts

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v4.widget.NestedScrollView
import com.contributetech.scripts.application.ScriptsApplication
import com.contributetech.scripts.database.DBCallBacks
import com.contributetech.scripts.database.MovieDetail
import com.contributetech.scripts.database.MovieDetailDao
import com.contributetech.scripts.homescreen.HomeScreenFragment
import com.contributetech.scripts.homescreen.MoviesFragmentContract
import com.contributetech.scripts.homescreen.PagerExperienceTypeAdapter
import com.contributetech.scripts.network.ParamsUtil
import com.contributetech.scripts.network.TMDBApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MoviesFragmentContract.ActivityContract{

    @Inject
    lateinit var api:TMDBApi

    @Inject
    lateinit var mMovieDetailsDao:MovieDetailDao

    lateinit var vpExperienceType:ViewPager
    lateinit var vpPageChangeListener:ViewPager.OnPageChangeListener
    lateinit var adapterExperienceType:PagerExperienceTypeAdapter

    var subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as ScriptsApplication).mAppComponent.inject((this))
        initView()
    }

    private fun initView() {
        var nsvHomeContainer:NestedScrollView = findViewById(R.id.nsv_home_scrool_container)
        nsvHomeContainer.isFillViewport = true

        vpExperienceType = findViewById(R.id.vp_experience_type)
        vpPageChangeListener = object:ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                val frag:HomeScreenFragment? = adapterExperienceType.getFragment(position)
                if(frag != null) {
                    if(frag.nowShowingMovieList.isEmpty()) {
                        frag.mActivityContract.fetchMovies()
                    }
                }
            }

        };
        vpExperienceType.addOnPageChangeListener(vpPageChangeListener)
        adapterExperienceType = PagerExperienceTypeAdapter(supportFragmentManager, this as MoviesFragmentContract.ActivityContract)
        vpExperienceType.adapter = adapterExperienceType
        vpPageChangeListener.onPageSelected(0)
    }

    override fun fetchMovies() {
        fetchNowShowingMoviesResults()
        fetchPopularMoviesResults()
        fetchUpcomingMoviesResults()
        fetchTopRatedMoviesResults()
    }

    private fun fetchNowShowingMoviesResults() {
        val contract:MoviesFragmentContract.FragmentContract = adapterExperienceType.getFragment(vpExperienceType.currentItem) as MoviesFragmentContract.FragmentContract
        val disposable:Disposable = api.getNowPlayingMovies(ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({    it -> contract.handleNowShowingResults(it) }, {error -> handleError(error) })
        subscriptions.add(disposable)
    }

    private fun fetchPopularMoviesResults() {
        val contract:MoviesFragmentContract.FragmentContract = adapterExperienceType.getFragment(vpExperienceType.currentItem) as MoviesFragmentContract.FragmentContract
        val disposable:Disposable = api.getPopularMovies(ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({    it -> contract.handlePopularMoviesResults(it) }, {error -> handleError(error) })
        subscriptions.add(disposable)
    }

    private fun fetchUpcomingMoviesResults() {
        val contract:MoviesFragmentContract.FragmentContract = adapterExperienceType.getFragment(vpExperienceType.currentItem) as MoviesFragmentContract.FragmentContract
        val disposable:Disposable = api.getUpcomingMovies(ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({    it -> contract.handleUpcomingMoviesResults(it) }, {error -> handleError(error) })
        subscriptions.add(disposable)
    }

    private fun fetchTopRatedMoviesResults() {
        val contract:MoviesFragmentContract.FragmentContract = adapterExperienceType.getFragment(vpExperienceType.currentItem) as MoviesFragmentContract.FragmentContract
        val disposable:Disposable = api.getTopRatedMovies(ParamsUtil.getNowShowingParam())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({    it -> contract.handleTopRatedMoviesResults(it) }, {error -> handleError(error) })
        subscriptions.add(disposable)
    }

    private fun handleError(error:Throwable) {
    }

    override fun storeMoviesForNowPlaying(moviesList: List<MovieDetail>): ArrayList<Int> {
        val contract:MoviesFragmentContract.FragmentContract = adapterExperienceType.getFragment(vpExperienceType.currentItem) as MoviesFragmentContract.FragmentContract
        val newList:MutableList<Int> = ArrayList();
        for(movie: MovieDetail in moviesList) {
            newList.add(movie.id)
        }
        val thread = Thread({
            mMovieDetailsDao.insertMoviesList(moviesList)
            runOnUiThread() {
                contract.subscribeToNowPlaying()
            }
        })
        thread.start()
        return newList as ArrayList<Int>;
    }

    override fun storeMoviesForUpcoming(moviesList: List<MovieDetail>): ArrayList<Int> {
        val contract:MoviesFragmentContract.FragmentContract = adapterExperienceType.getFragment(vpExperienceType.currentItem) as MoviesFragmentContract.FragmentContract
        val newList:MutableList<Int> = ArrayList();
        for(movie: MovieDetail in moviesList) {
            newList.add(movie.id)
        }
        val thread = Thread({
            mMovieDetailsDao.insertMoviesList(moviesList)
            runOnUiThread() {
                contract.subscribeToUpcomingMovies()
            }
        })
        thread.start()
        return newList as ArrayList<Int>;
    }

    override fun storeMoviesForTopRated(moviesList: List<MovieDetail>): ArrayList<Int> {
        val contract:MoviesFragmentContract.FragmentContract = adapterExperienceType.getFragment(vpExperienceType.currentItem) as MoviesFragmentContract.FragmentContract
        val newList:MutableList<Int> = ArrayList();
        for(movie: MovieDetail in moviesList) {
            newList.add(movie.id)
        }
        val thread = Thread({
            mMovieDetailsDao.insertMoviesList(moviesList as ArrayList<MovieDetail>)
            runOnUiThread() {
                contract.subscribeToTopRatedMovies()
            }
        })
        thread.start()
        return newList as ArrayList<Int>;
    }

    override fun storeMoviesForPopular(moviesList: List<MovieDetail>): ArrayList<Int> {
        val contract:MoviesFragmentContract.FragmentContract = adapterExperienceType.getFragment(vpExperienceType.currentItem) as MoviesFragmentContract.FragmentContract
        val newList:MutableList<Int> = ArrayList();
        for(movie: MovieDetail in moviesList) {
                newList.add(movie.id)
        }
        val thread = Thread({
            mMovieDetailsDao.insertMoviesList(moviesList)
            runOnUiThread() {
                contract.subscribeToPopularMovies()
            }
        })
        thread.start()
        return newList as ArrayList<Int>;
    }

    override fun fetchMovieListFromDb(movieIds: ArrayList<Int>, callback:DBCallBacks.Movies.MovieListTask) {
        mMovieDetailsDao.getMoviesByIds(movieIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it -> callback.onSuccess(it)
                }, {
                    it -> callback.onFailure(it)
                })
    }
}


