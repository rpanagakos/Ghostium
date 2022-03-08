package com.example.ghostzilla.database.room

import androidx.room.TypeConverter
import com.example.ghostzilla.models.coingecko.tredings.Coin
import com.google.gson.Gson

class DatabaseConverter {

    @TypeConverter
    fun listToJsonString(value: List<Coin>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<Coin>::class.java).toList()

}