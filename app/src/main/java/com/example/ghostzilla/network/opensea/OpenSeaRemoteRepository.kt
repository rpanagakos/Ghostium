package com.example.ghostzilla.network.opensea

import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.opensea.Assets
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface OpenSeaRemoteRepository {
    suspend fun getAllNfts(): GenericResponse<Assets>

}