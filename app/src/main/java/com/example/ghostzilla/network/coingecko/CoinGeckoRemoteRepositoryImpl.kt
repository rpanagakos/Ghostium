package com.example.ghostzilla.network.coingecko

import com.example.ghostzilla.di.coingecko.CoinGeckoNetwork
import com.example.ghostzilla.di.common.TypeEnum
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.coingecko.Cryptos
import com.example.ghostzilla.models.coingecko.charts.CoinPrices
import com.example.ghostzilla.models.coingecko.coin.Coin
import com.example.ghostzilla.models.coingecko.tredings.TredingCoins
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.utils.NetworkConnectivity
import com.google.gson.JsonObject
import javax.inject.Inject

class CoinGeckoRemoteRepositoryImpl @Inject constructor(
    @CoinGeckoNetwork(TypeEnum.APISERVICE) private val coinGeckoApi: CoinGeckoApi,
    private val networkConnectivity: NetworkConnectivity
) : CoinGeckoRemoteRepository {

    override suspend fun getAllCryptos(currency : String): GenericResponse<Cryptos> {
        return when (val response = networkConnectivity.processCall{(coinGeckoApi::getPriceVolatility)(
            currency,
            50
        )}) {
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
            currency)
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