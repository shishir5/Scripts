package com.contributetech.scripts.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Review (
        @PrimaryKey
        var id:String,

        var author:String,
        var content:String,
        var url:String
)