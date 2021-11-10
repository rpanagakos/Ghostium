package com.example.ghostzilla.network.covalent

import retrofit2.Response
import retrofit2.http.GET

interface CovalentApi {

    @GET("/v1/pricing/volatility/")
    suspend fun getPriceVolatility(): Response<String>
}