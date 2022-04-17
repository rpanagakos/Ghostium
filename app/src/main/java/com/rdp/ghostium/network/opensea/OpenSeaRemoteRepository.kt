package com.rdp.ghostium.network.opensea

import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.models.opensea.Assets
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface OpenSeaRemoteRepository {
    suspend fun getAllNfts(): GenericResponse<Assets>

}