package com.contributetech.scripts.application

import com.contributetech.scripts.MainActivity
import dagger.Component
import com.contributetech.scripts.application.ScriptsApplication
import com.contributetech.scripts.database.RoomModule
import com.contributetech.scripts.network.NetworkModule
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by zc-shishirdwivedi on 29/04/18.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, RoomModule::class))
interface AppComponent{
    fun inject(mMainActivity: MainActivity)
}

