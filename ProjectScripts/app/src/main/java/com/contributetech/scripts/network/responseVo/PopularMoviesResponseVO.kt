package com.contributetech.scripts.network.responseVo

import com.contributetech.scripts.database.MovieDetail
import com.google.gson.annotations.SerializedName

data class PopularMoviesResponseVO(val page:Int,
                                   @SerializedName("total_results") val totalResults:Int,
                                   @SerializedName("total_pages") val totalPages:Int,
                                   val results:List<MovieDetail>)