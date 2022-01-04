package com.example.ghostzilla.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class Low24h(
    @SerializedName("aed")
    val aed: Double= 0.0,
    @SerializedName("eur")
    val eur: Double= 0.0,
    @SerializedName("gbp")
    val gbp: Double= 0.0,
    @SerializedName("usd")
    val usd: Double= 0.0
)