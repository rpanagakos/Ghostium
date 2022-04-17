package com.rdp.ghostium.network.opensea

import com.rdp.ghostium.di.opensea.OpenSeaNetwork
import com.rdp.ghostium.di.common.TypeEnum
import com.rdp.ghostium.models.generic.GenericResponse
import com.rdp.ghostium.models.opensea.Assets
import com.rdp.ghostium.utils.NetworkConnectivity
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