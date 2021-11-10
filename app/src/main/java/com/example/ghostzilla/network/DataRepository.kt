package com.example.ghostzilla.network

import com.example.ghostzilla.di.IoDispatcher
import com.example.ghostzilla.models.generic.GenericResponse
import com.example.ghostzilla.network.covalent.CovalentRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataRepository @Inject constructor(private val remoteRepository: CovalentRemoteRepository, @IoDispatcher private val ioDispatcher: CoroutineDispatcher) {

    suspend fun requestData(): Flow<GenericResponse<Any>> {
        return flow {
            emit(remoteRepository.getSpotPrices())
        }.flowOn(ioDispatcher)
    }
}