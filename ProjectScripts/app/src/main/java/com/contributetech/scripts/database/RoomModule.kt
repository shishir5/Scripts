package com.contributetech.scripts.database

import com.contributetech.scripts.application.ScriptsApplication
import dagger.Module
import android.arch.persistence.room.Room
import com.contributetech.scripts.database.moviesDetail.MovieDetailDao
import com.contributetech.scripts.database.tvDetail.TvShowDetailDao
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(mApplication: ScriptsApplication) {

    var mdatabase:TMDBDatabase = Room.databaseBuilder(mApplication, TMDBDatabase::class.java, "tmdb-db").fallbackToDestructiveMigration().build();

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

    @Singleton
    @Provides
    fun getTvDetailsDao(mdatabase: TMDBDatabase): TvShowDetailDao {
        return mdatabase.getTvDetailDao()
    }
}