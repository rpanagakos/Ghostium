package com.example.ghostzilla.models.coingecko.coin


import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("description")
    val description: Description,
    @SerializedName("genesis_date")
    val genesisDate: String,
    @SerializedName("hashing_algorithm")
    val hashingAlgorithm: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: Image,
    @SerializedName("links")
    val links: Links,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int,
    @SerializedName("market_data")
    val marketData: MarketData,
    @SerializedName("name")
    val name: String,
    @SerializedName("platforms")
    val platforms: Platforms,
    @SerializedName("public_interest_score")
    val publicInterestScore: Double,
    @SerializedName("symbol")
    val symbol: String
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = when (obj) {
        is CryptoItem -> false
        else -> false
    }
}