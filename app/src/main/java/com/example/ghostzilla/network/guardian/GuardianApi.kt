package com.example.ghostzilla.network.guardian

import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.guardian.GuardianResponse
import com.example.ghostzilla.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface GuardianApi {

    @GET("/search?")
    suspend fun getLatestNews(
        @Query("q") content : String = Constants.GUARDIAN_CONTENT,
        @Query("order-by") order : String,
        @Query("show-fields") showFields : String = Constants.GUARDIAN_FIELDS
    ) : GenericResponse<GuardianResponse>
}