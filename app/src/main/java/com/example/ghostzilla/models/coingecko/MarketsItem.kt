package com.example.ghostzilla.models.coingecko


import com.example.ghostzilla.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class MarketsItem(
    @SerializedName("ath_change_percentage")
    val athChangePercentage: Double,
    @SerializedName("atl_change_percentage")
    val atlChangePercentage: Double,
    @SerializedName("current_price")
    val currentPrice: Float,
    @SerializedName("high_24h")
    val high24h: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("low_24h")
    val low24h: Double,
    @SerializedName("market_cap")
    val marketCap: Long,
    @SerializedName("max_supply")
    val maxSupply: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("price_change_24h")
    val priceChange24h: Double,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerializedName("symbol")
    val symbol: String
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = when (obj) {
        is MarketsItem -> name == obj.name && currentPrice == obj.currentPrice && symbol == obj.symbol
        else -> false
    }
}