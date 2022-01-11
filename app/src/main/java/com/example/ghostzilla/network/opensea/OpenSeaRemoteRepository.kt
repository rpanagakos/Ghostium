package com.example.ghostzilla.network.opensea

import com.example.ghostzilla.models.coingecko.Cryptos
import com.example.ghostzilla.models.generic.GenericResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface OpenSeaRemoteRepository {
    suspend fun getAllNfts(): GenericResponse<Cryptos>

}