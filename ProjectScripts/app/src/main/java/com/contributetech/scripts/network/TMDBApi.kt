package com.contributetech.scripts.network

import com.contributetech.scripts.database.movieDetails.MovieDetail
import com.contributetech.scripts.network.responseVo.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface TMDBApi {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@QueryMap param : Map<String, String>) : Observable<ShowListResponseVO>

    @GET("movie/popular")
    fun getPopularMovies(@QueryMap param : Map<String, String>) : Observable<ShowsResponseVO>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@QueryMap param : Map<String, String>) : Observable<ShowsResponseVO>

    @GET("movie/upcoming")
    fun getUpcomingMovies(@QueryMap param : Map<String, String>) : Observable<ShowListResponseVO>

    @GET("tv/on_the_air")
    fun getTvOnAir(@QueryMap param : Map<String, String>) : Observable<TvShowsResponseVO>

    @GET("tv/popular")
    fun getPopularTv(@QueryMap param : Map<String, String>) : Observable<TvShowsResponseVO>

    @GET("tv/airing_today")
    fun getTvAiringToday(@QueryMap param : Map<String, String>) : Observable<TvShowsResponseVO>

    @GET("tv/top_rated")
    fun getTopRatedTv(@QueryMap param : Map<String, String>) : Observable<TvShowsResponseVO>

    @GET("movie/{id}")
    fun getMovieDetail(@Path(value = "id", encoded = true) id:Int, @QueryMap param : Map<String, String>) : Observable<MovieDetail>

    @GET("collection/{collection_id}")
    fun getCollectionDetail(@Path(value = "collection_id", encoded = true) id:Int, @QueryMap param : Map<String, String>) : Observable<CollectionResponseVO>

    @GET("movie/{movie_id}/credits")
    fun getCredits(@Path(value = "movie_id", encoded = true) id:Int, @QueryMap param : Map<String, String>) : Observable<CreditResponseVO>

    @GET("movie/{movie_id}/reviews")
    fun getReviews(@Path(value = "movie_id", encoded = true) id:Int, @QueryMap param : Map<String, String>) : Observable<ReviewResponseVO>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(@Path(value = "movie_id", encoded = true) id:Int, @QueryMap param : Map<String, String>) : Observable<SimilarMoviesResponseVO>
}