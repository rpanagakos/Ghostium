package com.example.ghostzilla.network.covalent

import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.pricing.PriceVolatility
import retrofit2.Response
import javax.inject.Inject

class CovalentRemoteRepositoryImpl @Inject constructor(
    private val covalentApi: CovalentApi) : CovalentRemoteRepository {

    override suspend fun getPriceVolatility(): Response<GenericResponse<PriceVolatility>> {
         return covalentApi.getPriceVolatility()
    }

    override suspend fun getSpotPrices(): Response<GenericResponse<PriceVolatility>> {
        return covalentApi.getSpotPrices()
    }

}