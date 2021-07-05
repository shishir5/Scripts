package com.contributetech.scripts.database.tvDetails

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class SeasonsItemDetail (

        @PrimaryKey
        var id:Int,

        @ColumnInfo(name = "air_date")
        var airDate:String?,

        @ColumnInfo(name = "episode_count")
        var episodeCount:Int,

        var name:String?,

        var overview:String?,

        @ColumnInfo(name="poster_path")
        var posterPath:String?,

        @ColumnInfo(name="season_number")
        var seasonNumber:Int
        )