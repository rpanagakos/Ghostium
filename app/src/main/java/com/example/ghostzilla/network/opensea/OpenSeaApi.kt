package com.example.ghostzilla.network.opensea

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenSeaApi {

    @GET("/api/v1/assets/")
    suspend fun getAllNfts(
        @Query("limit") limit : String = "50"
    ): Response<List<Any>>

}