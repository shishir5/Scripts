package com.contributetech.scripts.homescreen

import com.contributetech.scripts.database.DBCallBacks
import com.contributetech.scripts.database.MovieDetail
import com.contributetech.scripts.network.responseVo.NowShowingResponseVO
import com.contributetech.scripts.network.responseVo.PopularMoviesResponseVO

class MoviesFragmentContract {
    interface ActivityContract {
        fun fetchMovies()
        fun storeMoviesForNowPlaying(moviesList: List<MovieDetail>):ArrayList<Int>
        fun storeMoviesForUpcoming(moviesList: List<MovieDetail>):ArrayList<Int>
        fun storeMoviesForTopRated(moviesList: List<MovieDetail>):ArrayList<Int>
        fun storeMoviesForPopular(moviesList: List<MovieDetail>):ArrayList<Int>
        fun fetchMovieListFromDb(moviesIds: ArrayList<Int>, callback:DBCallBacks.Movies.MovieListTask)
    }
    interface FragmentContract {
        fun handleNowShowingResults(nowShowingResponse: NowShowingResponseVO)
        fun handlePopularMoviesResults(popularMoviesResult: PopularMoviesResponseVO)
        fun handleUpcomingMoviesResults(upComingMovies: NowShowingResponseVO)
        fun handleTopRatedMoviesResults(topRatedMovies: PopularMoviesResponseVO)
        fun subscribeToNowPlaying()
        fun subscribeToTopRatedMovies()
        fun subscribeToPopularMovies()
        fun subscribeToUpcomingMovies()
    }
}