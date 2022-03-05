package com.example.ghostzilla.models.coingecko.tredings


import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("item")
    val item: Item
)