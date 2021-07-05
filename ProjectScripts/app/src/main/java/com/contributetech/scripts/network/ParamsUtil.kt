package com.contributetech.scripts.network

import javax.inject.Singleton

object ParamsUtil {

    object ParamsKey{
        val API_KEY: String = "api_key"
        val LANGUAGE: String = "language"
    }

    fun getApiToken() : Pair<String, String> {
            return Pair(ParamsKey.API_KEY, NetworkModule.API_KEY)
    }

    fun getLanguage() : Pair<String, String> {
        return Pair(ParamsKey.LANGUAGE, "en")
    }

    fun getNowShowingParam():Map<String, String> {
        val param:HashMap<String, String> = HashMap()
        param.put(ParamsKey.API_KEY, NetworkModule.API_KEY)
        param.put(ParamsKey.LANGUAGE, "en")
        return param
    }
}