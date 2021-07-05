package com.contributetech.scripts.util

import javax.inject.Singleton

@Singleton
class ImageUtil {
    val original:String = "original"

    class LandscapeSizes {
        companion object {
            val small_size:String = "w300/"
            val mid_size:String = "w780/"
            val large_size:String = "w1280/"
        }
    }
    class PortraitSizes {
        companion object {
            val small_size:String = "w154/"
            val mid_size:String = "w342/"
            val large_size:String = "w500/"
            val xlarge_size:String = "w780/"
        }
    }
}