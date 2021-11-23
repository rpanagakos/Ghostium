package com.example.ghostzilla.models.coingecko


import com.example.ghostzilla.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class MarketsItem(
    @SerializedName("current_price")
    val currentPrice: Float,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("low_24h")
    val low24h: Double? = null,
    @SerializedName("market_cap")
    val marketCap: Long? = null,
    @SerializedName("max_supply")
    val maxSupply: Double? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("price_change_24h")
    val priceChange24h: Float? = null,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Float,
    @SerializedName("symbol")
    val symbol: String
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = when (obj) {
        is MarketsItem -> name == obj.name && currentPrice == obj.currentPrice && symbol == obj.symbol
        else -> false
    }
}