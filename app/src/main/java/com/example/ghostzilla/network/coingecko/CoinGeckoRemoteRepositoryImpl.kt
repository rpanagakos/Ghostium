package com.example.ghostzilla.network.coingecko

import com.example.ghostzilla.models.coingecko.Cryptos
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.coingecko.coin.Coin
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.utils.NetworkConnectivity
import javax.inject.Inject

class CoinGeckoRemoteRepositoryImpl @Inject constructor(
    private val coinGeckoApi: CoinGeckoApi, private val networkConnectivity: NetworkConnectivity
) :
    CoinGeckoRemoteRepository {

    override suspend fun getAllCryptos(): GenericResponse<Cryptos> {
        return when (val response =
            networkConnectivity.processCall(coinGeckoApi::getPriceVolatility)) {
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
}