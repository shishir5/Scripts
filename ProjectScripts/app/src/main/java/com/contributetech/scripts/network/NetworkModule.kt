package com.contributetech.scripts.network

import android.app.Application
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.SECONDS




@Singleton
@Module
class NetworkModule {
    companion object {
        val BASE_URL = "https://api.themoviedb.org/3/"
        val API_KEY = "f3d75dd5e96c84ac8b6d3f2299d9576b"
    }

    @Singleton @Provides
    fun provideRetrofit(gson: Gson, client:OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(NetworkModule.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

    @Provides
    fun getOkHttpClient(mApplication:Application): OkHttpClient {
        val okhttpClientBuilder = OkHttpClient.Builder()
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)
        okhttpClientBuilder.addInterceptor(ChuckInterceptor(mApplication))
        return okhttpClientBuilder.build()
    }

    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    fun createApiService(retrofit: Retrofit): TMDBApi {
        return retrofit.create(TMDBApi::class.java)
    }
}