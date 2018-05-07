package com.contributetech.scripts.database.moviesDetail

import android.arch.persistence.room.*
import io.reactivex.Maybe


@Dao
interface MovieDetailDao {

    @Query("Select * from MovieDetail where id in (:ids)")
    fun getMoviesByIds(ids: List<Int>) : Maybe<List<MovieDetail>>

    @Query("Select * from MovieDetail")
    fun getAllMovies() : List<MovieDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesList(movies:List<MovieDetail>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieDetail)

    @Update
    fun updateMovie(movie: MovieDetail)

    @Update
    fun updateMoviesList(movies:List<MovieDetail>)

    @Delete
    fun deleteMovie(movie: MovieDetail)

    @Delete
    fun deleteMoviesList(movies:List<MovieDetail>)
}