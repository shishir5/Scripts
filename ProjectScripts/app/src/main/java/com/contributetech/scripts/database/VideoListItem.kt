package com.contributetech.scripts.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class VideoListItem (
        @PrimaryKey
        val id:String,

        val key:String,
        val name:String,
        val site:String
)