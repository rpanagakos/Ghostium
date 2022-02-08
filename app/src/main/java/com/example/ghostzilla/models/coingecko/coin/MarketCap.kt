package com.example.ghostzilla.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class MarketCap(
    @SerializedName("aud")
    val aed: Long = 0L,
    @SerializedName("eur")
    val eur: Long= 0L,
    @SerializedName("gbp")
    val gbp: Long= 0L,
    @SerializedName("usd")
    val usd: Long= 0L
)