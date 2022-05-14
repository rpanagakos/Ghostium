package com.rdp.ghostium.models.coingecko.search


import com.google.gson.annotations.SerializedName
import com.rdp.ghostium.abstraction.LocalModel

data class CoinResult(
    @SerializedName("id")
    private val _id: String?,
    @SerializedName("large")
    private val _large: String?,
    @SerializedName("market_cap_rank")
    private val _marketCapRank: Int?,
    @SerializedName("name")
    private val _name: String?,
    @SerializedName("symbol")
    private val _symbol: String?,
    @SerializedName("thumb")
    private val _thumb: String?
) : LocalModel {

    val symbol
        get() = "($_symbol)"

    val id
        get() = _id ?: ""

    val name
        get() = _name ?: ""

    val thumb
        get() = _thumb ?: ""


    override fun equalsContent(obj: LocalModel): Boolean {
        return true
    }
}