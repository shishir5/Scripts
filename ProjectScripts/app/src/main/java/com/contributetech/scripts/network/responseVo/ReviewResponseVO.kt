package com.contributetech.scripts.network.responseVo

import com.contributetech.scripts.database.Review

data class ReviewResponseVO(
            var id:Int,
            var page:Int,
            var results:ArrayList<Review>
)