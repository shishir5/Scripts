package com.contributetech.scripts.database

import com.contributetech.scripts.database.moviesDetail.MovieDetail
import com.contributetech.scripts.database.tvDetail.TvShowDetail

class DBCallBacks {
    class Movies {
        interface MovieListTask {
            fun onSuccess(movieList: List<MovieDetail>)
            fun onFailure(error:Throwable)
        }
        interface TvListTask {
            fun onSuccess(movieList: List<TvShowDetail>)
            fun onFailure(error:Throwable)
        }
    }
}