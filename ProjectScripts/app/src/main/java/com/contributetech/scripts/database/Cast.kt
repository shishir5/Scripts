package com.contributetech.scripts.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Cast(
        @PrimaryKey
        var id:Int,

        var character:String,
        var name:String,
        var gender:Int,
        var profilePath:String
)