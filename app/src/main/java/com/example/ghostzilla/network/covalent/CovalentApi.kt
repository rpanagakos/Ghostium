package com.example.ghostzilla.network.covalent

import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.models.pricing.PriceVolatility
import retrofit2.Response
import retrofit2.http.GET

interface CovalentApi {

    //Pricing EndPoint

    @GET("/v1/pricing/tickers/")
    suspend fun getSpotPrices(): Response<GenericResponse<PriceVolatility>>

    @GET("/v1/pricing/volatility/")
    suspend fun getPriceVolatility(): Response<GenericResponse<PriceVolatility>>
}