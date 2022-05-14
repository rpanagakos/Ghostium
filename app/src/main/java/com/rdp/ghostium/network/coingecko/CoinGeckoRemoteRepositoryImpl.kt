package com.rdp.ghostium.network.coingecko

import com.google.gson.JsonObject
import com.rdp.ghostium.di.coingecko.CoinGeckoNetwork
import com.rdp.ghostium.di.common.TypeEnum
import com.rdp.ghostium.models.coingecko.CryptoItem
import com.rdp.ghostium.models.coingecko.Cryptos
import com.rdp.ghostium.models.coingecko.charts.CoinPrices
import com.rdp.ghostium.models.coingecko.coin.Coin
import com.rdp.ghostium.models.coingecko.search.CoinsSearched
import com.rdp.ghostium.models.coingecko.tredings.TredingCoins
import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.utils.NetworkConnectivity
import javax.inject.Inject

class CoinGeckoRemoteRepositoryImpl @Inject constructor(
    @CoinGeckoNetwork(TypeEnum.APISERVICE) private val coinGeckoApi: CoinGeckoApi,
    private val networkConnectivity: NetworkConnectivity
) : CoinGeckoRemoteRepository {

    override suspend fun getAllCryptos(currency: String): GenericResponse<Cryptos> {
        return when (val response = networkConnectivity.processCall {
            (coinGeckoApi::getPriceVolatility)(
                currency,
                50
            )
        }) {
            is List<*> -> GenericResponse.Success(data = Cryptos(response as ArrayList<CryptoItem>))
            else -> GenericResponse.DataError(errorCode = response as Int)
        }
    }

    override suspend fun getCoinSearchResult(coinID: String): GenericResponse<Coin> {
        return when (val response = networkConnectivity.processCall {
            (coinGeckoApi::getCoinSearchResult)(
                coinID,
                false,
                false,
                true
            )
        }) {
            is Coin -> GenericResponse.Success(data = response)
            else -> GenericResponse.DataError(errorCode = response as Int)
        }
    }

    override suspend fun getCoinSearch(coinID: String): GenericResponse<CoinsSearched> {
        return when (val response = networkConnectivity.processCall {
            (coinGeckoApi::getCoinSearch)(
                coinID
            )
        }) {
            is CoinsSearched -> GenericResponse.Success(data = response)
            else -> GenericResponse.DataError(errorCode = response as Int)
        }
    }


    override suspend fun getCoinChartDetails(
        coinID: String,
        days: Int,
        currency: String
    ): GenericResponse<CoinPrices> {
        return when (val response = networkConnectivity.processCall {
            (coinGeckoApi::getCoinChartDetails)(
                coinID,
                days.toString(),
                currency
            )
        }) {
            is CoinPrices -> GenericResponse.Success(data = response)
            else -> GenericResponse.DataError(errorCode = response as Int)
        }
    }

    override suspend fun getFavouritesPrices(
        ids: String,
        currency: String,
    ): GenericResponse<JsonObject> {
        return when (val response = networkConnectivity.processCall {
            (coinGeckoApi::getFavouritesPrices)(
                ids,
                currency
            )
        }) {
            is JsonObject -> GenericResponse.Success(data = response)
            else -> GenericResponse.DataError(errorCode = response as Int)
        }
    }

    override suspend fun getTrendingCryptos(): GenericResponse<TredingCoins> {
        return when (val response =
            networkConnectivity.processCall(coinGeckoApi::getTredingCryptos)) {
            is TredingCoins -> GenericResponse.Success(data = response)
            else -> GenericResponse.DataError(errorCode = response as Int)
        }
    }
}