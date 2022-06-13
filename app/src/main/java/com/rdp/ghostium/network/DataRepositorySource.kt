package com.rdp.ghostium.network

import com.google.gson.JsonObject
import com.rdp.ghostium.models.coingecko.Cryptos
import com.rdp.ghostium.models.coingecko.charts.CoinPrices
import com.rdp.ghostium.models.coingecko.coin.Coin
import com.rdp.ghostium.models.coingecko.search.CoinsSearched
import com.rdp.ghostium.models.coingecko.tredings.TredingCoins
import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.models.opensea.Assets
import kotlinx.coroutines.flow.Flow

interface DataRepositorySource {
    suspend fun requestData(): Flow<GenericResponse<Cryptos>>
    suspend fun searchCoin(coinID: String): Flow<GenericResponse<Coin>>
    suspend fun searchCoins(coinID: String): Flow<GenericResponse<CoinsSearched>>
    suspend fun getTredingCryptos(): Flow<GenericResponse<TredingCoins>>
    suspend fun getCoinChartDetails(coinID: String, days: Int): Flow<GenericResponse<CoinPrices>>
    suspend fun getFavouritesPrices(cryptosIds: String): Flow<GenericResponse<JsonObject>>
    suspend fun getAllNfts(): Flow<GenericResponse<Assets>>
}