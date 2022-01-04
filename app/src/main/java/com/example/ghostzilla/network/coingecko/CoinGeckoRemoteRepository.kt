package com.example.ghostzilla.network.coingecko

import com.example.ghostzilla.models.coingecko.Cryptos
import com.example.ghostzilla.models.coingecko.coin.Coin
import com.example.ghostzilla.models.generic.GenericResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface CoinGeckoRemoteRepository {
    suspend fun getAllCryptos(): GenericResponse<Cryptos>
    suspend fun getCoinSearchResult(coinID: String): GenericResponse<Coin>
}