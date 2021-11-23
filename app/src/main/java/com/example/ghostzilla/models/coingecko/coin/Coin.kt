package com.example.ghostzilla.models.coingecko.coin


import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.coingecko.MarketsItem
import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("block_time_in_minutes")
    val blockTimeInMinutes: Int,
    @SerializedName("categories")
    val categories: List<String>,
    @SerializedName("coingecko_score")
    val coingeckoScore: Double,
    @SerializedName("community_score")
    val communityScore: Double,
    @SerializedName("description")
    val description: Description,
    @SerializedName("developer_score")
    val developerScore: Double,
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
    @SerializedName("liquidity_score")
    val liquidityScore: Double,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int,
    @SerializedName("market_data")
    val marketData: MarketData,
    @SerializedName("name")
    val name: String,
    @SerializedName("platforms")
    val platforms: Platforms,
    @SerializedName("public_interest_score")
    val publicInterestScore: Int,
    @SerializedName("sentiment_votes_down_percentage")
    val sentimentVotesDownPercentage: Double,
    @SerializedName("sentiment_votes_up_percentage")
    val sentimentVotesUpPercentage: Double,
    @SerializedName("symbol")
    val symbol: String
): LocalModel {
    override fun equalsContent(obj: LocalModel): Boolean = when (obj) {
        is MarketsItem -> false
        else -> false
    }
}