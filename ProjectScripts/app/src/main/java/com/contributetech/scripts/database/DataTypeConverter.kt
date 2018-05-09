package com.contributetech.scripts.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken






class DataTypeConverter {
    val gson: Gson = Gson()
    @TypeConverter
    fun stringToIntList(data: String): List<Int> {
        val idList: List<Int> = emptyList()
        if (data.length > 2) {
            val newData = data.substring(1, data.length - 1)
            val ids: List<String> = newData.split(",")
            for (id: String in ids) {
                idList.plus(id.toInt())
            }
        }
        return idList
    }

    @TypeConverter
    fun intListToString(list: List<Int>): String {
        return gson.toJson(list).toString();
    }

    @TypeConverter
    fun jsonObjToGenreList(genres: String): ArrayList<Genre> {
        return Gson().fromJson(genres, object : TypeToken<ArrayList<Genre>>() {}.type)
    }

    @TypeConverter
    fun GenreListToJsonObjString(genres: ArrayList<Genre>): String {
        return gson.toJson(genres).toString();
    }

    @TypeConverter
    fun jsonObjToPCList(pcArrayString: String): ArrayList<ProductionCompany> {
        return Gson().fromJson(pcArrayString, object : TypeToken<ArrayList<ProductionCompany>>() {}.type)

    }

    @TypeConverter
    fun PCListToJsonObjString(pcs: ArrayList<ProductionCompany>): String {
        return gson.toJson(pcs).toString();
    }
}

