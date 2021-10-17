package com.example.ghostzilla.models.pricing


import com.example.ghostzilla.abstraction.LocalModel
import com.google.gson.annotations.SerializedName

data class Crypto(
    @SerializedName("contract_address")
    val contractAddress: String,
    @SerializedName("contract_decimals")
    val contractDecimals: Int,
    @SerializedName("contract_name")
    val contractName: String,
    @SerializedName("contract_ticker_symbol")
    val contractTickerSymbol: String,
    @SerializedName("logo_url")
    val logoUrl: String,
    @SerializedName("quote_rate")
    val quoteRate: Float = 0f,
    @SerializedName("stddev_16h")
    val stddev16h: Float = 0f,
    @SerializedName("stddev_1h")
    val stddev1h: Any,
    @SerializedName("stddev_24h")
    val stddev24h: Float = 0f,
    @SerializedName("stddev_2h")
    val stddev2h: Float = 0f,
    @SerializedName("stddev_4h")
    val stddev4h: Float = 0f,
    @SerializedName("stddev_8h")
    val stddev8h: Float = 0f,
    @SerializedName("supports_erc")
    val supportsErc: Any,
    @SerializedName("rank")
    val rank: Int? = null
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = false
}