package com.contributetech.scripts.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class DataTypeConverter {
    val gson:Gson = Gson()
    @TypeConverter
    fun stringToIntList(data: String):List<Int> {
        val idList:List<Int> = emptyList()
        if(data.length > 2) {
            val newData = data.substring(1, data.length-1)
            val ids:List<String> = newData.split(",")
            for(id:String in ids){
                idList.plus(id.toInt())
            }
        }
        return idList
    }
    @TypeConverter
    fun intListToString(list: List<Int>) : String {
        return gson.toJson(list).toString();
    }
}

