package com.example.ghostzilla.network.covalent

import com.example.ghostzilla.models.generic.GenericResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface CovalentRemoteRepository {
    suspend fun getSpotPrices() : GenericResponse<Any>
}