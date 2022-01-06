package com.example.ghostzilla.models.coingecko.charts


import com.example.ghostzilla.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class CoinPrices(
    @SerializedName("prices")
    val prices: List<List<Any>>
): LocalModel{
    override fun equalsContent(obj: LocalModel): Boolean = false
}