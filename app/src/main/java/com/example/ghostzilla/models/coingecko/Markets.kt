package com.example.ghostzilla.models.coingecko

import com.example.ghostzilla.abstraction.LocalModel

data class Cryptos(
    val CryptosList: ArrayList<CryptoItem>
    ): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}