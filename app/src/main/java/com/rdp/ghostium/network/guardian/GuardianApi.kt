package com.rdp.ghostium.network.guardian

import com.rdp.ghostium.models.guardian.GuardianResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GuardianApi {

    @GET("/search?")
    suspend fun getLatestNews(
        @Query("q") content : String,
        @Query("order-by") order : String,
        @Query("show-fields") showFields : String,
        @Query("page") page : Int,
        @Query("page-size") pageSize : Int
    ) : GuardianResponse
}