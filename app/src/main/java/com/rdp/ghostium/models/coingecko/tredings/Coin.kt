package com.rdp.ghostium.models.coingecko.tredings

import com.rdp.ghostium.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("item")
    val item: Item
) : LocalModel{
    override fun equalsContent(obj: LocalModel): Boolean {
         return true
    }
}