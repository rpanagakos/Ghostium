package com.rdp.ghostium.di.common

import com.rdp.ghostium.network.coingecko.CoinGeckoRemoteRepository
import com.rdp.ghostium.network.coingecko.CoinGeckoRemoteRepositoryImpl
import com.rdp.ghostium.network.guardian.GuardianRemoteRepository
import com.rdp.ghostium.network.guardian.GuardianRemoteRepositoryIml
import com.rdp.ghostium.network.opensea.OpenSeaRemoteRepository
import com.rdp.ghostium.network.opensea.OpenSeaRemoteRepositoryImpl
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