package com.example.ghostzilla.di

import com.example.ghostzilla.network.covalent.CovalentRemoteRepository
import com.example.ghostzilla.network.covalent.CovalentRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideCovalentRemoteRepository(covalentRemoteRepositoryImpl: CovalentRemoteRepositoryImpl) : CovalentRemoteRepository
}