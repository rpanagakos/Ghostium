package com.example.ghostzilla.network.coingecko

import com.example.ghostzilla.models.coingecko.MarketsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface CoinGeckoApi {

    @GET("/api/v3/coins/markets/")
    suspend fun getPriceVolatility(
        @Query("vs_currency") currency: String = "eur",
        @Query("per_page") cryptos : Int = 30
    ): Response<List<MarketsItem>>
}