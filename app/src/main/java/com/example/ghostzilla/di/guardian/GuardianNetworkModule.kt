package com.example.ghostzilla.di.guardian

import com.example.ghostzilla.di.common.TypeEnum
import com.example.ghostzilla.network.coingecko.CoinGeckoApi
import com.example.ghostzilla.utils.Constants
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
object GuardianNetworkModule {

    @Singleton
    @Provides
    @GuardianNetwork(TypeEnum.HTTPLOGGING)
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Singleton
    @Provides
    @GuardianNetwork(TypeEnum.OKHTTP)
    fun provideHttpClient(
        @GuardianNetwork(TypeEnum.HTTPLOGGING) httpLoggingInterceptor: HttpLoggingInterceptor
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
    @GuardianNetwork(TypeEnum.GSON)
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    @Singleton
    @Provides
    @GuardianNetwork(TypeEnum.RETROFIT)
    fun provideRetrofitInstance(
        @GuardianNetwork(TypeEnum.OKHTTP) okHttpClient: OkHttpClient,
        @GuardianNetwork(TypeEnum.GSON) gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.COIN_GECKO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @GuardianNetwork(TypeEnum.APISERVICE)
    fun provideApiService(
        @GuardianNetwork(TypeEnum.RETROFIT) retrofit: Retrofit
    ): CoinGeckoApi {
        return retrofit.create(CoinGeckoApi::class.java)
    }

}