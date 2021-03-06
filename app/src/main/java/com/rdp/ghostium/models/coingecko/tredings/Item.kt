package com.rdp.ghostium.models.coingecko.tredings


import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.models.coingecko.CryptoItem
import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("coin_id")
    val coinId: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("large")
    val large: String,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("thumb")
    val thumb: String
) : LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean {
        return true
    }

    fun getItAsCryptoItem(): CryptoItem {
        return CryptoItem(id = id, name = name, symbol = symbol, image = large)
    }

}