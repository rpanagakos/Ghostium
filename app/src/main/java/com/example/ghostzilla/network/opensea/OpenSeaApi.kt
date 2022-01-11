package com.example.ghostzilla.network.opensea

import com.example.ghostzilla.models.opensea.Assets
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenSeaApi {

    @GET("/api/v1/assets/")
    suspend fun getAllNfts(
        @Query("limit") limit : String = "1"
    ): Response<Assets>

}