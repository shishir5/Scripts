package com.contributetech.scripts.network

class NetworkImageUtil {
    companion object {
        val IMAGE_BASE_URL: String = "https://image.tmdb.org/t/p/"

        fun getImagePath(path: String?, imageSize:String): String {
            var url: String = IMAGE_BASE_URL + imageSize + path
            return url
        }
    }
}