package com.example.ghostzilla.models.coingecko.tredings


import com.google.gson.annotations.SerializedName

data class TredingCoins(
    @SerializedName("coins")
    val coins: List<Coin>,
    @SerializedName("exchanges")
    val exchanges: List<Any>
)