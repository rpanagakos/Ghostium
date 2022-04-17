package com.rdp.ghostium.models.coingecko.tredings


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rdp.ghostium.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

@Entity(tableName = "trendings_table")
data class TredingCoins(
    @SerializedName("coins")
    val coins: List<Coin>,
    @PrimaryKey(autoGenerate = false)
    val id : Int = 0,
    var timetamps: Long = 0
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean {
        return true
    }

}