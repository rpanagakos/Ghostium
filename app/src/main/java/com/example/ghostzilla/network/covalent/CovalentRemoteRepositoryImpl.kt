package com.example.ghostzilla.network.covalent

import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.utils.NetworkConnectivity
import javax.inject.Inject

class CovalentRemoteRepositoryImpl @Inject constructor(
    private val covalentApi: CovalentApi, private val networkConnectivity: NetworkConnectivity) : CovalentRemoteRepository {

    override suspend fun getSpotPrices(): GenericResponse<Any> {
        return when(val response = networkConnectivity.processCall(covalentApi::getPriceVolatility)){
            is String -> GenericResponse.Success(data = response as String)
            else -> GenericResponse.DataError(errorCode = response as Int)
        }
    }
}