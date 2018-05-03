package com.contributetech.scripts.database

import com.contributetech.scripts.application.ScriptsApplication
import dagger.Module
import android.arch.persistence.room.Room
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(mApplication: ScriptsApplication) {

    var mdatabase:TMDBDatabase = Room.databaseBuilder(mApplication, TMDBDatabase::class.java, "tmdb-db").build();

    @Singleton
    @Provides
    fun provideTMDBDb   (): TMDBDatabase {
        return mdatabase
    }

    @Singleton
    @Provides
    fun getMovieDetailsDao(mdatabase: TMDBDatabase): MovieDetailDao {
        return mdatabase.getMovieDetailDao()
    }
}