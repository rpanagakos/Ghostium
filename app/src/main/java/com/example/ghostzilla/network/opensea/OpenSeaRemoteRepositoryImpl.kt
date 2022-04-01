package com.example.ghostzilla.network.opensea

import com.example.ghostzilla.di.opensea.OpenSeaNetwork
import com.example.ghostzilla.di.common.TypeEnum
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.opensea.Assets
import com.example.ghostzilla.utils.NetworkConnectivity
import javax.inject.Inject

class OpenSeaRemoteRepositoryImpl @Inject constructor(
    @OpenSeaNetwork(TypeEnum.APISERVICE) private val openSeaApi: OpenSeaApi,
    private val networkConnectivity: NetworkConnectivity
) : OpenSeaRemoteRepository {

    override suspend fun getAllNfts(): GenericResponse<Assets> {
        return when (val response =
            networkConnectivity.processCall(openSeaApi::getAllNfts)) {
            is Assets -> GenericResponse.Success(data = response)
            else -> GenericResponse.DataError(errorCode = response as Int)
        }    }

}