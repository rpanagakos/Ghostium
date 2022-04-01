package com.example.ghostzilla.di.common

import com.example.ghostzilla.network.coingecko.CoinGeckoRemoteRepository
import com.example.ghostzilla.network.coingecko.CoinGeckoRemoteRepositoryImpl
import com.example.ghostzilla.network.guardian.GuardianRemoteRepository
import com.example.ghostzilla.network.guardian.GuardianRemoteRepositoryIml
import com.example.ghostzilla.network.opensea.OpenSeaRemoteRepository
import com.example.ghostzilla.network.opensea.OpenSeaRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideCoinGeckoRemoteRepository(covalentRemoteRepositoryImpl: CoinGeckoRemoteRepositoryImpl) : CoinGeckoRemoteRepository

    @Binds
    abstract fun provideOpenSeaRemoteRepository(openSeaRemoteRepositoryImpl: OpenSeaRemoteRepositoryImpl) : OpenSeaRemoteRepository

    @Binds
    abstract fun provideGuardianRemoteRepository(guardianRemoteRepositoryIml: GuardianRemoteRepositoryIml) : GuardianRemoteRepository
}