package com.contributetech.scripts.database.tvDetail

import android.arch.persistence.room.*
import io.reactivex.Maybe

@Dao
interface TvShowDetailDao {

    @Query("Select * from TvShowDetail where id in (:ids)")
    fun getTvShowsById(ids: List<Int>): Maybe<List<TvShowDetail>>

    @Query("Select * from TvShowDetail")
    fun getAllTvShows(): List<TvShowDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowList(movies: List<TvShowDetail>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(movie: TvShowDetail)

    @Update
    fun updateTvShow(movie: TvShowDetail)

    @Update
    fun updateTvShowList(movies: List<TvShowDetail>)

    @Delete
    fun deleteTvShow(movie: TvShowDetail)

    @Delete
    fun deleteTvShowList(movies: List<TvShowDetail>)
}