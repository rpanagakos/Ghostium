package com.example.ghostzilla.network.coingecko

import com.example.ghostzilla.models.coingecko.Markets
import com.example.ghostzilla.models.coingecko.MarketsItem
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.utils.NetworkConnectivity
import javax.inject.Inject

class CoinGeckoRemoteRepositoryImpl @Inject constructor(
    private val coinGeckoApi: CoinGeckoApi, private val networkConnectivity: NetworkConnectivity) :
    CoinGeckoRemoteRepository {

    override suspend fun getSpotPrices(): GenericResponse<Markets> {
        return when(val response = networkConnectivity.processCall(coinGeckoApi::getPriceVolatility)){
            is List<*> -> GenericResponse.Success(data = Markets(response as ArrayList<MarketsItem>))
            else -> GenericResponse.DataError(errorCode = response as Int)
        }
    }
}