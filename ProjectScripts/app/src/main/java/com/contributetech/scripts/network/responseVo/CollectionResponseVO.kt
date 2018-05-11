package com.contributetech.scripts.network.responseVo

import android.arch.persistence.room.ColumnInfo
import com.contributetech.scripts.database.moviesListItemDetail.MovieListItem
import com.google.gson.annotations.SerializedName

data class CollectionResponseVO (
val id:Int,

var name:String?,

@SerializedName("poster_path")
var posterPath:String?,

@SerializedName("backdrop_path")
var backdropPath:String?,

var parts:ArrayList<MovieListItem>

)