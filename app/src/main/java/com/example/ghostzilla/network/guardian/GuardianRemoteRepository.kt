package com.example.ghostzilla.network.guardian

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.ghostzilla.models.coingecko.CryptoItem
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.guardian.GuardianResponse
import com.example.ghostzilla.models.guardian.Result
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface GuardianRemoteRepository {
    suspend fun getLatestNews(content : String, orderBy : String, showFields : String) : GenericResponse<GuardianResponse>
    suspend fun getLatestNewsDummy(): LiveData<PagingData<Result>>
}