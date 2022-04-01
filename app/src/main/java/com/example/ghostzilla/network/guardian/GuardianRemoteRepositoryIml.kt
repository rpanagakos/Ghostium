package com.example.ghostzilla.network.guardian

import com.example.ghostzilla.di.common.TypeEnum
import com.example.ghostzilla.di.guardian.GuardianNetwork
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.guardian.GuardianResponse
import com.example.ghostzilla.utils.NetworkConnectivity
import javax.inject.Inject

class GuardianRemoteRepositoryIml @Inject constructor(
    @GuardianNetwork(TypeEnum.APISERVICE) private val guardianApi: GuardianApi,
    private val networkConnectivity: NetworkConnectivity
) : GuardianRemoteRepository {

    override suspend fun getLatestNews(
        content: String,
        orderBy: String,
        showFields: String
    ): GenericResponse<GuardianResponse> {
        TODO("Not yet implemented")
    }

}