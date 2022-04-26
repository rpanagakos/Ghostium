package com.rdp.ghostium.network.guardian

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.di.common.TypeEnum
import com.rdp.ghostium.di.guardian.GuardianNetwork
import com.rdp.ghostium.models.settings.TitleRecyclerItem
import javax.inject.Inject

class GuardianRemoteRepositoryIml @Inject constructor(
    @GuardianNetwork(TypeEnum.APISERVICE) private val guardianApi: GuardianApi
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