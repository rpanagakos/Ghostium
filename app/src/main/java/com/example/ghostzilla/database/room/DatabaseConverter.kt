package com.example.ghostzilla.database.room

import androidx.room.TypeConverter
import com.example.ghostzilla.abstraction.LocalModel
import com.google.gson.Gson

class DatabaseConverter {

    @TypeConverter
    fun listToJsonString(value: List<LocalModel>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<LocalModel>::class.java).toList()

}