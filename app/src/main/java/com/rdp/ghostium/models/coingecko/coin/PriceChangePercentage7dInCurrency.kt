package com.rdp.ghostium.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class PriceChangePercentage7dInCurrency(
    @SerializedName("aud")
    val aud: Double,
    @SerializedName("eur")
    val eur: Double,
    @SerializedName("gbp")
    val gbp: Double,
    @SerializedName("usd")
    val usd: Double
)