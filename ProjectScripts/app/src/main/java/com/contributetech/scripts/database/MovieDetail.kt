package com.contributetech.scripts.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.SerializedName


@TypeConverters(DataTypeConverter::class)
@Entity
class MovieDetail (
        @PrimaryKey var id:Int,
        @ColumnInfo(name = "vote_count") var voteCount:Int,
        @ColumnInfo(name = "video") var hasVideo:Boolean,
        @ColumnInfo(name = "vote_average") var voteAverage:Float,
        var title:String,
        @ColumnInfo(name = "popularity") var popularityRating:Float,
        @ColumnInfo(name = "poster_path") var posterPath:String,
        @ColumnInfo(name = "original_language") var originalLanguage:String,
        @ColumnInfo(name = "original_title") var originalTitle:String,
        @ColumnInfo(name = "genre_ids")
        @SerializedName("genre_ids")
        var genres:List<Int>,
        @ColumnInfo(name = "backdrop_path") var backdropPath:String?,
        var adult:Boolean,
        var overview:String,
        @ColumnInfo(name = "release_date") var releaseDate:String
        )