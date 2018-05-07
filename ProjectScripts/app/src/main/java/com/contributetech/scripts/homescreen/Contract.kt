package com.contributetech.scripts.homescreen

import com.contributetech.scripts.database.DBCallBacks
import com.contributetech.scripts.database.moviesDetail.MovieDetail
import com.contributetech.scripts.database.tvDetail.TvShowDetail
import com.contributetech.scripts.network.responseVo.ShowListResponseVO
import com.contributetech.scripts.network.responseVo.ShowsResponseVO
import com.contributetech.scripts.network.responseVo.TvShowsResponseVO

class Contract {
    class Movies {
        interface ActivityContract {
            fun fetchMovies()
            fun storeMoviesForNowPlaying(moviesList: List<MovieDetail>):ArrayList<Int>
            fun storeMoviesForUpcoming(moviesList: List<MovieDetail>):ArrayList<Int>
            fun storeMoviesForTopRated(moviesList: List<MovieDetail>):ArrayList<Int>
            fun storeMoviesForPopular(moviesList: List<MovieDetail>):ArrayList<Int>
            fun fetchMovieListFromDb(moviesIds: ArrayList<Int>, callback:DBCallBacks.Movies.MovieListTask)
        }
        interface FragmentContract {
            fun handleNowShowingResults(nowShowingResponse: ShowListResponseVO)
            fun handlePopularMoviesResults(popularMoviesResult: ShowsResponseVO)
            fun handleUpcomingMoviesResults(upComingMovies: ShowListResponseVO)
            fun handleTopRatedMoviesResults(topRatedMovies: ShowsResponseVO)
            fun subscribeToNowPlaying()
            fun subscribeToTopRatedMovies()
            fun subscribeToPopularMovies()
            fun subscribeToUpcomingMovies()
        }
    }
    class TvShows {
        interface ActivityContract {
            fun fetchTvShows()
            fun storeAiringTodayForTv(moviesList: List<TvShowDetail>):ArrayList<Int>
            fun storeOnTheAirShowsForTv(moviesList: List<TvShowDetail>):ArrayList<Int>
            fun storePopularShowsForTv(moviesList: List<TvShowDetail>):ArrayList<Int>
            fun storeTopRatedShowsForTv(moviesList: List<TvShowDetail>):ArrayList<Int>
            fun fetchTvListFromDb(moviesIds: ArrayList<Int>, callback:DBCallBacks.Movies.TvListTask)
        }
        interface FragmentContract {
            fun handleTvResult(nowShowingResponse: TvShowsResponseVO, listType: String)
            fun subscribeToAiringToday()
            fun subscribeToTopRatedTv()
            fun subscribeToPopularTv()
            fun subscribeToOnAirTv()
        }
    }
}