package com.example.ghostzilla.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class MarketCap(
    @SerializedName("aed")
    val aed: Long,
    @SerializedName("eur")
    val eur: Long,
    @SerializedName("gbp")
    val gbp: Long,
    @SerializedName("usd")
    val usd: Long
)