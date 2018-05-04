package com.contributetech.scripts.database

class DBCallBacks {
    class Movies {
        interface MovieListTask {
            fun onSuccess(movieList: List<MovieDetail>)
            fun onFailure(error:Throwable)
        }
    }
}