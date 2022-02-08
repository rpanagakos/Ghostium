package com.example.ghostzilla.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class PriceChangePercentage24hInCurrency(
    @SerializedName("aud")
    val aud: Double,
    @SerializedName("eur")
    val eur: Double,
    @SerializedName("gbp")
    val gbp: Double,
    @SerializedName("usd")
    val usd: Double
)