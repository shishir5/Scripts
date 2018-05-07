package com.contributetech.scripts.homescreen

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.contributetech.scripts.R
import com.contributetech.scripts.database.DBCallBacks
import com.contributetech.scripts.database.moviesDetail.MovieDetail
import com.contributetech.scripts.network.responseVo.ShowListResponseVO
import com.contributetech.scripts.network.responseVo.ShowsResponseVO

class HomeMoviesFragment:Fragment(), Contract.Movies.FragmentContract {

    var nowShowingMovieList:ArrayList<Int> = arrayListOf()
    var upcomingMovieList:ArrayList<Int> = arrayListOf()
    var topRatedMovieList:ArrayList<Int> = arrayListOf()
    var popularMovieList:ArrayList<Int> = arrayListOf()

    lateinit var vpCarousel: ViewPager
    lateinit var pagerAdapterMovie:MovieCarouselPagerAdapter

    lateinit var rvPopularMovies: RecyclerView
    lateinit var rvUpcomingMovies: RecyclerView
    lateinit var rvTopRatedMovies: RecyclerView
    lateinit var adapterUpcomingMovies:HorizontalMovieListRecyclerAdapter
    lateinit var adapterTopRatedMovies:HorizontalMovieListRecyclerAdapter
    lateinit var adapterPopularMovies:HorizontalMovieListRecyclerAdapter

    lateinit var mActivityContract:Contract.Movies.ActivityContract

    companion object {
        val NOW_SHOWING_MOVIES:String = "NOW_SHOWING_MOVIES"
        val POPULAR_MOVIES:String = "POPULAR_MOVIES"
        val UPCOMING_MOVIES:String = "UPCOMING_MOVIES"
        val TOP_RATED_MOVIES:String = "TOP_RATED_MOVIES"

        fun newInstance():HomeMoviesFragment {
            val fragment = HomeMoviesFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_home_screen, container, false)
        initView(view)
        return view
    }

    private fun initView(view:View) {
        val context:Context = activity as Context
        vpCarousel = view.findViewById(R.id.vp_carousel)
        pagerAdapterMovie = MovieCarouselPagerAdapter(context)
        vpCarousel.adapter = pagerAdapterMovie
        adapterPopularMovies = HorizontalMovieListRecyclerAdapter(context)
        adapterTopRatedMovies = HorizontalMovieListRecyclerAdapter(context)
        adapterUpcomingMovies = HorizontalMovieListRecyclerAdapter(context)

        rvPopularMovies = view.findViewById(R.id.rv_popular_movies)
        rvTopRatedMovies = view.findViewById(R.id.rv_top_rated_movies)
        rvUpcomingMovies = view.findViewById(R.id.rv_upcoming_movies)
        rvPopularMovies.isNestedScrollingEnabled = false
        rvTopRatedMovies.isNestedScrollingEnabled = false
        rvUpcomingMovies.isNestedScrollingEnabled = false
        rvPopularMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvTopRatedMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvUpcomingMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvPopularMovies.adapter = adapterPopularMovies
        rvUpcomingMovies.adapter = adapterUpcomingMovies
        rvTopRatedMovies.adapter = adapterTopRatedMovies
    }

    override fun subscribeToNowPlaying() {
        if(!(activity?.isFinishing)!!) {
            mActivityContract.fetchMovieListFromDb(nowShowingMovieList, object:DBCallBacks.Movies.MovieListTask {
                override fun onSuccess(movieList: List<MovieDetail>) {
                    setNowPlayingList(movieList)
                }

                override fun onFailure(error:Throwable) {
                    handleError(error)
                }

            })
        }
    }

    override fun subscribeToPopularMovies() {
        if(!(activity?.isFinishing)!!) {
            mActivityContract.fetchMovieListFromDb(popularMovieList, object:DBCallBacks.Movies.MovieListTask {
                override fun onSuccess(movieList: List<MovieDetail>) {
                    setPopuLarMovieList(movieList)
                }

                override fun onFailure(error:Throwable) {
                    handleError(error)
                }

            })
        }

    }

    override fun subscribeToTopRatedMovies() {
        if(!(activity?.isFinishing)!!) {
            mActivityContract.fetchMovieListFromDb(topRatedMovieList, object:DBCallBacks.Movies.MovieListTask {
                override fun onSuccess(movieList: List<MovieDetail>) {
                    setTopRatedMovieList(movieList)
                }

                override fun onFailure(error:Throwable) {
                    handleError(error)
                }

            })
        }

    }

    override fun subscribeToUpcomingMovies() {
        if(!(activity?.isFinishing)!!) {
            mActivityContract.fetchMovieListFromDb(upcomingMovieList, object:DBCallBacks.Movies.MovieListTask {
                override fun onSuccess(movieList: List<MovieDetail>) {
                    setUpComingMovieList(movieList)
                }

                override fun onFailure(error:Throwable) {
                    handleError(error)
                }

            })
        }

    }

    private fun setUpComingMovieList(movieList:List<MovieDetail>) {
        if(!movieList.isEmpty())
            adapterUpcomingMovies.setData(movieList as ArrayList<MovieDetail>)
    }

    private fun setTopRatedMovieList(movieList:List<MovieDetail>) {
        if(!movieList.isEmpty())
            adapterTopRatedMovies.setData(movieList as ArrayList<MovieDetail>)
    }

    private fun setPopuLarMovieList(movieList:List<MovieDetail>) {
        if(!movieList.isEmpty())
            adapterPopularMovies.setData(movieList as ArrayList<MovieDetail>)
    }

    private fun setNowPlayingList(movieList:List<MovieDetail>) {
        if(!movieList.isEmpty())
            pagerAdapterMovie.setData(movieList as ArrayList<MovieDetail>)
    }

    private fun handleError(error:Throwable) {
    }

    override fun handleNowShowingResults(nowShowingResponse: ShowListResponseVO) {
        val moviesList : List<MovieDetail> = nowShowingResponse.results;
        nowShowingMovieList = mActivityContract.storeMoviesForNowPlaying(moviesList);
    }


    override fun handlePopularMoviesResults(popularMoviesResult: ShowsResponseVO) {
        val moviesList : List<MovieDetail> = popularMoviesResult.results;
        popularMovieList = mActivityContract.storeMoviesForPopular(moviesList);
    }

    override fun handleUpcomingMoviesResults(upcomingMovies: ShowListResponseVO) {
        val moviesList : List<MovieDetail> = upcomingMovies.results;
        upcomingMovieList = mActivityContract.storeMoviesForUpcoming(moviesList);
    }

    override fun handleTopRatedMoviesResults(topRatedMovies: ShowsResponseVO) {
        val moviesList : List<MovieDetail> = topRatedMovies.results;
        topRatedMovieList = mActivityContract.storeMoviesForTopRated(moviesList);
    }
}