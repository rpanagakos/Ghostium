package com.example.ghostzilla.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class PriceChangePercentage24hInCurrency(
    @SerializedName("aed")
    val aed: Double,
    @SerializedName("eur")
    val eur: Double,
    @SerializedName("gbp")
    val gbp: Double,
    @SerializedName("usd")
    val usd: Double
)