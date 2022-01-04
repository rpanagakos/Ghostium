package com.example.ghostzilla.network

import com.example.ghostzilla.di.IoDispatcher
import com.example.ghostzilla.models.coingecko.Markets
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

    suspend fun requestData(): Flow<GenericResponse<Markets>> {
        return flow {
            emit(remoteRepository.getAllCryptos())
        }.flowOn(ioDispatcher)
    }

    suspend fun searchCoin(coinID: String): Flow<GenericResponse<Coin>> {
        return flow {
            emit(remoteRepository.getCoinSearchResult(coinID))
        }.flowOn(ioDispatcher)
    }
}