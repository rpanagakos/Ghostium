package com.example.ghostzilla.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class Sparkline7d(
    @SerializedName("price")
    val price: List<Double>
)