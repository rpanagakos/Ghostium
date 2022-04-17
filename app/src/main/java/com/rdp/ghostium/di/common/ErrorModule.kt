package com.rdp.ghostium.di.common

import com.rdp.ghostium.models.errors.ErrorManager
import com.rdp.ghostium.models.errors.ErrorUseCase
import com.rdp.ghostium.models.errors.mapper.ErrorMapper
import com.rdp.ghostium.models.errors.mapper.ErrorMapperSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperSource
}
