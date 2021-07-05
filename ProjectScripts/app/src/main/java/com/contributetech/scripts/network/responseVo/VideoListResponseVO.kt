package com.contributetech.scripts.network.responseVo

import com.contributetech.scripts.database.VideoListItem


data class VideoListResponseVO(
        val id:Int,
        val results:ArrayList<VideoListItem>
)