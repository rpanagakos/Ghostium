package com.example.ghostzilla.network.guardian

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.settings.TitleRecyclerItem
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface GuardianRemoteRepository {
    suspend fun getLatestNews(title : TitleRecyclerItem): LiveData<PagingData<LocalModel>>
}