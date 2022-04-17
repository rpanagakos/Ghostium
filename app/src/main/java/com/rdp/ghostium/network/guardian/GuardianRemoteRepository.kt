package com.rdp.ghostium.network.guardian

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.rdp.ghostium.abstraction.LocalModel
import com.rdp.ghostium.models.settings.TitleRecyclerItem
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface GuardianRemoteRepository {
    suspend fun getLatestNews(title : TitleRecyclerItem): LiveData<PagingData<LocalModel>>
}