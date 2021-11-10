package com.example.ghostzilla.di

import android.content.Context
import com.example.ghostzilla.network.DataRepository
import com.example.ghostzilla.network.covalent.CovalentRemoteRepository
import com.example.ghostzilla.network.covalent.CovalentRemoteRepositoryImpl
import com.example.ghostzilla.utils.Network
import com.example.ghostzilla.utils.NetworkConnectivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideCovalentRemoteRepository(covalentRemoteRepositoryImpl: CovalentRemoteRepositoryImpl) : CovalentRemoteRepository

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context): NetworkConnectivity {
        return Network(context)
    }
}