package com.rdp.ghostium.models.coingecko.search


import com.google.gson.annotations.SerializedName

data class CoinsSearched(
    @SerializedName("coins")
    val coinResults: List<CoinResult>
)