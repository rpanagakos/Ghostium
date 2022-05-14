package com.rdp.ghostium.network.coingecko

import com.google.gson.JsonObject
import com.rdp.ghostium.models.coingecko.Cryptos
import com.rdp.ghostium.models.coingecko.charts.CoinPrices
import com.rdp.ghostium.models.coingecko.coin.Coin
import com.rdp.ghostium.models.coingecko.search.CoinsSearched
import com.rdp.ghostium.models.coingecko.tredings.TredingCoins
import com.rdp.ghostium.models.generic.GenericResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface CoinGeckoRemoteRepository {
    suspend fun getAllCryptos(currency : String): GenericResponse<Cryptos>
    suspend fun getCoinSearchResult(coinID: String): GenericResponse<Coin>
    suspend fun getCoinSearch(coinID: String): GenericResponse<CoinsSearched>
    suspend fun getCoinChartDetails(coinID: String, days: Int, currency : String): GenericResponse<CoinPrices>
    suspend fun getFavouritesPrices(ids: String, currency : String): GenericResponse<JsonObject>
    suspend fun getTrendingCryptos(): GenericResponse<TredingCoins>
}