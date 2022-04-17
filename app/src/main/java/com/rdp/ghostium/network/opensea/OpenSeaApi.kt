package com.rdp.ghostium.network.opensea

import com.rdp.ghostium.models.opensea.Assets
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenSeaApi {

    @GET("/api/v1/assets/")
    suspend fun getAllNfts(
        @Query("limit") limit : String = "50"
    ): Response<Assets>

}