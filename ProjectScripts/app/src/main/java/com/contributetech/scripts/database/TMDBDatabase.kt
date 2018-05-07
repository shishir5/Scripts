package com.contributetech.scripts.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.contributetech.scripts.database.moviesDetail.MovieDetail
import com.contributetech.scripts.database.moviesDetail.MovieDetailDao
import com.contributetech.scripts.database.tvDetail.TvShowDetail
import com.contributetech.scripts.database.tvDetail.TvShowDetailDao

@Database(entities = arrayOf(MovieDetail::class, TvShowDetail::class), version = 1, exportSchema = false)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun getMovieDetailDao(): MovieDetailDao
    abstract fun getTvDetailDao(): TvShowDetailDao
}

