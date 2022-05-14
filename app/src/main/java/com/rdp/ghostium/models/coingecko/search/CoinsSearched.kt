package com.rdp.ghostium.models.coingecko.search


import com.google.gson.annotations.SerializedName
import com.rdp.ghostium.abstraction.LocalModel

data class CoinsSearched(
    @SerializedName("coins")
    val coinResults: List<CoinResult>
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean {
        return true
    }

}