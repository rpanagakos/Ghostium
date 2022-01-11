package com.example.ghostzilla.network.opensea

import com.example.ghostzilla.di.OpenSeaNetwork
import com.example.ghostzilla.di.TypeEnum
import com.example.ghostzilla.utils.NetworkConnectivity
import javax.inject.Inject

class OpenSeaRemoteRepositoryImpl @Inject constructor(
    @OpenSeaNetwork(TypeEnum.APISERVICE) private val openSeaApi: OpenSeaApi,
    private val networkConnectivity: NetworkConnectivity
) : OpenSeaRemoteRepository {

}