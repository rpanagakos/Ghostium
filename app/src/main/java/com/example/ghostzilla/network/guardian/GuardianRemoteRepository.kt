package com.example.ghostzilla.network.guardian

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.guardian.GuardianResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface GuardianRemoteRepository {
    suspend fun getLatestNews(): LiveData<PagingData<LocalModel>>
}