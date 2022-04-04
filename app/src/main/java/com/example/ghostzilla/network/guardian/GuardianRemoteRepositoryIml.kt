package com.example.ghostzilla.network.guardian

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.di.common.TypeEnum
import com.example.ghostzilla.di.guardian.GuardianNetwork
import com.example.ghostzilla.models.settings.TitleRecyclerItem
import com.example.ghostzilla.utils.NetworkConnectivity
import javax.inject.Inject

class GuardianRemoteRepositoryIml @Inject constructor(
    @GuardianNetwork(TypeEnum.APISERVICE) private val guardianApi: GuardianApi,
    private val networkConnectivity: NetworkConnectivity
) : GuardianRemoteRepository {

    override suspend fun getLatestNews(title : TitleRecyclerItem): LiveData<PagingData<LocalModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GuardianListPagingSource(guardianApi = guardianApi, title = title)
            }
        ).liveData
    }

}