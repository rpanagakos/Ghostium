package com.rdp.ghostium.models.coingecko.coin


import com.google.gson.annotations.SerializedName

data class MarketData(
    @SerializedName("ath")
    val ath: Ath,
    @SerializedName("ath_change_percentage")
    val athChangePercentage: AthChangePercentage,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double,
    @SerializedName("current_price")
    val currentPrice: CurrentPrice,
    @SerializedName("fdv_to_tvl_ratio")
    val fdvToTvlRatio: Any,
    @SerializedName("high_24h")
    val high24h: Price24h,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("low_24h")
    val low24h: Price24h,
    @SerializedName("market_cap")
    val marketCap: MarketCap,
    @SerializedName("market_cap_change_24h")
    val marketCapChange24h: Any,
    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double,
    @SerializedName("market_cap_rank")
    val marketCapRank: Int,
    @SerializedName("max_supply")
    val maxSupply: Double,
    @SerializedName("mcap_to_tvl_ratio")
    val mcapToTvlRatio: Any,
    @SerializedName("price_change_24h")
    val priceChange24h: Double,
    @SerializedName("price_change_24h_in_currency")
    val priceChange24hInCurrency: PriceChange24hInCurrency,
    @SerializedName("price_change_percentage_1h_in_currency")
    val priceChangePercentage1hInCurrency: PriceChangePercentage1hInCurrency,
    @SerializedName("price_change_percentage_1y")
    val priceChangePercentage1y: Double,
    @SerializedName("price_change_percentage_1y_in_currency")
    val priceChangePercentage1yInCurrency: PriceChangePercentage1yInCurrency,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double,
    @SerializedName("price_change_percentage_24h_in_currency")
    val priceChangePercentage24hInCurrency: PriceChangePercentage24hInCurrency,
    @SerializedName("price_change_percentage_30d")
    val priceChangePercentage30d: Double,
    @SerializedName("price_change_percentage_30d_in_currency")
    val priceChangePercentage30dInCurrency: PriceChangePercentage30dInCurrency,
    @SerializedName("price_change_percentage_7d")
    val priceChangePercentage7d: Double,
    @SerializedName("price_change_percentage_7d_in_currency")
    val priceChangePercentage7dInCurrency: PriceChangePercentage7dInCurrency,
    @SerializedName("sparkline_7d")
    val sparkline7d: Sparkline7d,
    @SerializedName("total_supply")
    val totalSupply: Double,
    @SerializedName("total_value_locked")
    val totalValueLocked: Any
)