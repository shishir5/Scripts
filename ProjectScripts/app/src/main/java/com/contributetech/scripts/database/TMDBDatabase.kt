package com.contributetech.scripts.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(MovieDetail::class), version = 1, exportSchema = false)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun getMovieDetailDao(): MovieDetailDao
}

