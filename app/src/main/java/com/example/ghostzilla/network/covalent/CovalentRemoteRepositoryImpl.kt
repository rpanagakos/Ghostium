package com.example.ghostzilla.network.covalent

import javax.inject.Inject

class CovalentRemoteRepositoryImpl @Inject constructor(
    private val covalentApi: CovalentApi) :
    CovalentRemoteRepository {
}