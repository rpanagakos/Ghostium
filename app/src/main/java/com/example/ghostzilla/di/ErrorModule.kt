package com.example.ghostzilla.di

import com.example.ghostzilla.models.errors.ErrorManager
import com.example.ghostzilla.models.errors.ErrorUseCase
import com.example.ghostzilla.models.errors.mapper.ErrorMapper
import com.example.ghostzilla.models.errors.mapper.ErrorMapperSource
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
