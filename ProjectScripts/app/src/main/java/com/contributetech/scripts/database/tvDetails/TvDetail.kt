package com.contributetech.scripts.database.tvDetails

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import com.contributetech.scripts.database.Cast
import com.contributetech.scripts.database.DataTypeConverter
import com.contributetech.scripts.database.Genre
import com.contributetech.scripts.database.ProductionCompany
import com.google.gson.annotations.SerializedName

@TypeConverters(DataTypeConverter::class)
@Entity
class TvDetail (
        @PrimaryKey
        var id:Int,

        @ColumnInfo(name = "backdrop_path")
        var backdropPath:String?,


        @ColumnInfo(name = "created_by")
        var createdBy:ArrayList<Cast>?,

        @ColumnInfo(name = "episode_run_time")
        var episodeDuration:Array<Int>,

        @ColumnInfo(name = "first_air_date")
        var firstAirDate:String?,

        @ColumnInfo(name = "last_air_date")
        var lastAirDate:String?,

        var genres:ArrayList<Genre>,

        var homepage:String?,

        var name:String?,

        var overview:String?,

        @ColumnInfo(name = "poster_path")
        var posterPath:String?,

        @ColumnInfo(name = "original_name")
        var originalName:String?,

        var networks:ArrayList<ProductionCompany>,

        @ColumnInfo(name = "number_of_episodes")
        var numOfEpisodes:Int,

        @ColumnInfo(name = "number_of_seasons")
        var numOfSeasons:Int,

        @ColumnInfo(name = "number_of_episodes")
        var productionCompaies:ArrayList<ProductionCompany>,

        var seasons:ArrayList<SeasonsItemDetail>,

        @ColumnInfo(name = "vote_count")
        var voteCount:Int,

        @ColumnInfo(name = "vote_average")
        @SerializedName( "vote_average")
        var voteAvg:Float
        )