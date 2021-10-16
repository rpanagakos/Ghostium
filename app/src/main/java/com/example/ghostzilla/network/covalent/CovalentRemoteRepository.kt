package com.example.ghostzilla.network.covalent

import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.pricing.PriceVolatility
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response

@ActivityRetainedScoped
interface CovalentRemoteRepository {

    suspend fun getPriceVolatility() : Response<GenericResponse<PriceVolatility>>

    suspend fun getSpotPrices() : Response<GenericResponse<PriceVolatility>>
}