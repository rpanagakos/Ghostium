package com.example.ghostzilla.di.coingecko

import android.content.Context
import com.example.ghostzilla.di.common.TypeEnum
import com.example.ghostzilla.network.coingecko.CoinGeckoApi
import com.example.ghostzilla.utils.Constants.Companion.COIN_GECKO_BASE_URL
import com.example.ghostzilla.utils.Network
import com.example.ghostzilla.utils.NetworkConnectivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoinGeckoNetworkModule {

    @Singleton
    @Provides
    @CoinGeckoNetwork(TypeEnum.HTTPLOGGING)
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    @Singleton
    @Provides
    @CoinGeckoNetwork(TypeEnum.OKHTTP)
    fun provideHttpClient(
        @CoinGeckoNetwork(TypeEnum.HTTPLOGGING) httpLoggingInterceptor: HttpLoggingInterceptor
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
    @CoinGeckoNetwork(TypeEnum.GSON)
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    @Singleton
    @Provides
    @CoinGeckoNetwork(TypeEnum.RETROFIT)
    fun provideRetrofitInstance(
        @CoinGeckoNetwork(TypeEnum.OKHTTP) okHttpClient: OkHttpClient,
        @CoinGeckoNetwork(TypeEnum.GSON) gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(COIN_GECKO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @CoinGeckoNetwork(TypeEnum.APISERVICE)
    fun provideApiService(
        @CoinGeckoNetwork(TypeEnum.RETROFIT) retrofit: Retrofit
    ): CoinGeckoApi {
        return retrofit.create(CoinGeckoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
        return Network(context)
    }

}