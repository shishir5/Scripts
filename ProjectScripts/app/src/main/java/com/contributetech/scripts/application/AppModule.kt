package com.contributetech.scripts.application
import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by zc-shishirdwivedi on 29/04/18.
 */
@Module
class AppModule (val mApplication: ScriptsApplication){

    @Provides @Singleton
    fun getApplication() : Application {
        return mApplication
    }
}