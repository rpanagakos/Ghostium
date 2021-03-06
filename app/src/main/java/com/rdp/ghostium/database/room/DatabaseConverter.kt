package com.rdp.ghostium.database.room

import androidx.room.TypeConverter
import com.rdp.ghostium.models.coingecko.tredings.Coin
import com.rdp.ghostium.models.guardian.Fields
import com.google.gson.Gson

class DatabaseConverter {

    @TypeConverter
    fun objectToJsonString(value: Fields?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToObject(value: String) = Gson().fromJson(value, Fields::class.java)

    @TypeConverter
    fun listToJsonString(value: List<Coin>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<Coin>::class.java).toList()

}