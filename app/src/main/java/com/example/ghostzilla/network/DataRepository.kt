package com.example.ghostzilla.network

import com.example.ghostzilla.di.IoDispatcher
import com.example.ghostzilla.models.coingecko.Cryptos
import com.example.ghostzilla.models.coingecko.charts.CoinPrices
import com.example.ghostzilla.models.coingecko.coin.Coin
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.coingecko.CoinGeckoRemoteRepository
import com.example.ghostzilla.network.opensea.OpenSeaRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val coinGeckoRemoteRepository: CoinGeckoRemoteRepository,
    private val openSeaRemoteRepository: OpenSeaRemoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun requestData(): Flow<GenericResponse<Cryptos>> {
        return flow {
            emit(coinGeckoRemoteRepository.getAllCryptos())
        }.flowOn(ioDispatcher)
    }

    suspend fun searchCoin(coinID: String): Flow<GenericResponse<Coin>> {
        return flow {
            emit(coinGeckoRemoteRepository.getCoinSearchResult(coinID))
        }.flowOn(ioDispatcher)
    }

    suspend fun getCoinChartDetails(coinID: String, days: Int): Flow<GenericResponse<CoinPrices>> {
        return flow {
            emit(coinGeckoRemoteRepository.getCoinChartDetails(coinID, days))
        }.flowOn(ioDispatcher)
    }
}