package com.example.ghostzilla.network.guardian

import com.example.ghostzilla.models.guardian.GuardianResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GuardianApi {

    @GET("/search?")
    suspend fun getLatestNews(
        @Query("q") content : String,
        @Query("order-by") order : String,
        @Query("show-fields") showFields : String
    ) : Response<GuardianResponse>
}