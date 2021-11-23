package com.example.ghostzilla.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class CurrentPrice(
    @SerializedName("aed")
    val aed: Int,
    @SerializedName("eur")
    val eur: Int,
    @SerializedName("gbp")
    val gbp: Int,
    @SerializedName("usd")
    val usd: Int
)