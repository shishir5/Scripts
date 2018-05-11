package com.contributetech.scripts.database.movieDetails

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Collection(
        @PrimaryKey
        var id:Int,

        var name:String?,

        @ColumnInfo(name = "poster_path")
        var posterPath:String?,

        @ColumnInfo(name = "backdrop_path")
        var backdropPath:String?
)