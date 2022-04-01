package com.example.ghostzilla.network.guardian

import com.example.ghostzilla.di.common.TypeEnum
import com.example.ghostzilla.di.guardian.GuardianNetwork
import com.example.ghostzilla.network.coingecko.CoinGeckoApi
import com.example.ghostzilla.utils.NetworkConnectivity
import javax.inject.Inject

class GuardianRemoteRepositoryIml @Inject constructor(
    @GuardianNetwork(TypeEnum.APISERVICE) private val guardianApi: GuardianApi,
    private val networkConnectivity: NetworkConnectivity
) : GuardianRemoteRepository {

}