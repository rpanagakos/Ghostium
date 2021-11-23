package com.example.ghostzilla.models.coingecko.coin

import com.example.ghostzilla.abstraction.LocalModel

data class CoinUiData(
    val id: String,
    val image: Image,
    val name: String,
    val symbol: String,
    val priceChangePercentage24h: Float,
    val currentPrice: Float
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}