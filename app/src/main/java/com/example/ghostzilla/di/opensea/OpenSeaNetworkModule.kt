package com.example.ghostzilla.di.opensea

import com.example.ghostzilla.di.common.TypeEnum
import com.example.ghostzilla.network.opensea.OpenSeaApi
import com.example.ghostzilla.utils.Constants.Companion.OPEN_SEA_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object OpenSeaNetworkModule {

    @Singleton
    @Provides
    @OpenSeaNetwork(TypeEnum.HTTPLOGGING)
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Singleton
    @Provides
    @OpenSeaNetwork(TypeEnum.OKHTTP)
    fun provideHttpClient(
        @OpenSeaNetwork(TypeEnum.HTTPLOGGING) httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .build()

                val requestBuilder = original.newBuilder().url(url)
                    .addHeader("Content-Type", "application/json")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }


    @Singleton
    @Provides
    @OpenSeaNetwork(TypeEnum.GSON)
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    @Singleton
    @Provides
    @OpenSeaNetwork(TypeEnum.RETROFIT)
    fun provideRetrofitInstance(
        @OpenSeaNetwork(TypeEnum.OKHTTP) okHttpClient: OkHttpClient,
        @OpenSeaNetwork(TypeEnum.GSON) gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(OPEN_SEA_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @OpenSeaNetwork(TypeEnum.APISERVICE)
    fun provideApiService(
        @OpenSeaNetwork(TypeEnum.RETROFIT) retrofit: Retrofit
    ): OpenSeaApi {
        return retrofit.create(OpenSeaApi::class.java)
    }

}