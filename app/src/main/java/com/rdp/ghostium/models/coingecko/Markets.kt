package com.rdp.ghostium.models.coingecko

import com.rdp.ghostium.abstraction.LocalModel

data class Cryptos(
    val CryptosList: ArrayList<CryptoItem>
    ): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}