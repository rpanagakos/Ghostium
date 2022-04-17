package com.rdp.ghostium.models.coingecko.charts


import com.rdp.ghostium.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class CoinPrices(
    @SerializedName("prices")
    val prices: List<List<Any>>
): LocalModel{
    override fun equalsContent(obj: LocalModel): Boolean = false
}