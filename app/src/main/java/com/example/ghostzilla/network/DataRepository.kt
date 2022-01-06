package com.example.ghostzilla.network

import com.example.ghostzilla.di.IoDispatcher
import com.example.ghostzilla.models.coingecko.Cryptos
import com.example.ghostzilla.models.coingecko.charts.CoinPrices
import com.example.ghostzilla.models.coingecko.coin.Coin
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.coingecko.CoinGeckoRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val remoteRepository: CoinGeckoRemoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun requestData(): Flow<GenericResponse<Cryptos>> {
        return flow {
            emit(remoteRepository.getAllCryptos())
        }.flowOn(ioDispatcher)
    }

    suspend fun searchCoin(coinID: String): Flow<GenericResponse<Coin>> {
        return flow {
            emit(remoteRepository.getCoinSearchResult(coinID))
        }.flowOn(ioDispatcher)
    }

    suspend fun getCoinChartDetails(coinID: String, days: Int): Flow<GenericResponse<CoinPrices>> {
        return flow {
            emit(remoteRepository.getCoinChartDetails(coinID, days))
        }.flowOn(ioDispatcher)
    }
}