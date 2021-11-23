package com.example.ghostzilla.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class Low24h(
    @SerializedName("aed")
    val aed: Float,
    @SerializedName("eur")
    val eur: Float,
    @SerializedName("gbp")
    val gbp: Float,
    @SerializedName("usd")
    val usd: Float
)