package com.contributetech.scripts.application

import android.app.Application
import com.contributetech.scripts.database.RoomModule
import com.contributetech.scripts.network.NetworkModule
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by zc-shishirdwivedi on 29/04/18.
 */

class ScriptsApplication : Application() {
    lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        mAppComponent = initDagger()
        Fresco.initialize(this)
    }

    private fun initDagger(): AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .networkModule(NetworkModule())
                    .roomModule(RoomModule(this))
                    .build()
}

