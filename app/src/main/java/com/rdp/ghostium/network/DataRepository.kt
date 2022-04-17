package com.rdp.ghostium.network

import com.rdp.ghostium.di.IoDispatcher
import com.rdp.ghostium.di.common.CurrencyImpl
import com.rdp.ghostium.models.coingecko.Cryptos
import com.rdp.ghostium.models.coingecko.charts.CoinPrices
import com.rdp.ghostium.models.coingecko.coin.Coin
import com.rdp.ghostium.models.coingecko.tredings.TredingCoins
import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.models.opensea.Assets
import com.rdp.ghostium.network.coingecko.CoinGeckoRemoteRepository
import com.rdp.ghostium.network.guardian.GuardianRemoteRepository
import com.rdp.ghostium.network.opensea.OpenSeaRemoteRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val coinGeckoRemoteRepository: CoinGeckoRemoteRepository,
    private val openSeaRemoteRepository: OpenSeaRemoteRepository,
    private val guardianRemoteRepository: GuardianRemoteRepository,
    val currencyImpl: CurrencyImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun requestData(): Flow<GenericResponse<Cryptos>> {
        return flow {
            emit(coinGeckoRemoteRepository.getAllCryptos(currencyImpl.getCurrency()))
        }.flowOn(ioDispatcher)
    }

    suspend fun searchCoin(coinID: String): Flow<GenericResponse<Coin>> {
        return flow {
            emit(coinGeckoRemoteRepository.getCoinSearchResult(coinID))
        }.flowOn(ioDispatcher)
    }

    suspend fun getTredingCryptos(): Flow<GenericResponse<TredingCoins>> {
        return flow {
            emit(coinGeckoRemoteRepository.getTrendingCryptos())
        }.flowOn(ioDispatcher)
    }

    suspend fun getCoinChartDetails(coinID: String, days: Int): Flow<GenericResponse<CoinPrices>> {
        return flow {
            emit(coinGeckoRemoteRepository.getCoinChartDetails(coinID, days, currencyImpl.getCurrency()))
        }.flowOn(ioDispatcher)
    }

    suspend fun getFavouritesPrices(cryptosIds: String): Flow<GenericResponse<JsonObject>> {
        return flow {
            emit(coinGeckoRemoteRepository.getFavouritesPrices(cryptosIds, currencyImpl.getCurrency()))
        }.flowOn(ioDispatcher)
    }

    suspend fun getAllNfts() : Flow<GenericResponse<Assets>> {
        return flow {
            emit(openSeaRemoteRepository.getAllNfts())
        }.flowOn(ioDispatcher)
    }
}