package com.example.ghostzilla.network.coingecko

import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.coingecko.charts.CoinPrices
import com.example.ghostzilla.models.coingecko.coin.Coin
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("/api/v3/coins/markets/")
    suspend fun getPriceVolatility(
        @Query("vs_currency") currency: String,
        @Query("per_page") cryptos: Int
    ): Response<List<CryptoItem>>

    @GET("/api/v3/coins/{id}")
    suspend fun getCoinSearchResult(
        @Path("id") coinID: String,
        @Query("localization") localization: Boolean,
        @Query("developer_data") developerData: Boolean,
        @Query("sparkline") sparkline: Boolean
    ): Response<Coin>

    @GET("/api/v3/coins/{id}/market_chart/")
    suspend fun getCoinChartDetails(
        @Path("id") coinID: String,
        @Query("days") days: String,
        @Query("vs_currency") currency: String
    ): Response<CoinPrices>

    @GET("/api/v3/simple/price/")
    suspend fun getFavouritesPrices(
        @Query("ids") ids: String,
        @Query("vs_currencies") currency: String
    ): Response<JsonObject>
}