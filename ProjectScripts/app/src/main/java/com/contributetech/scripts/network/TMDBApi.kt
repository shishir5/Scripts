package com.contributetech.scripts.network

import com.contributetech.scripts.network.responseVo.NowShowingResponseVO
import com.contributetech.scripts.network.responseVo.PopularMoviesResponseVO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface TMDBApi {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@QueryMap param : Map<String, String>) : Observable<NowShowingResponseVO>

    @GET("movie/popular")
    fun getPopularMovies(@QueryMap param : Map<String, String>) : Observable<PopularMoviesResponseVO>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@QueryMap param : Map<String, String>) : Observable<PopularMoviesResponseVO>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@QueryMap param : Map<String, String>) : Observable<NowShowingResponseVO>
}