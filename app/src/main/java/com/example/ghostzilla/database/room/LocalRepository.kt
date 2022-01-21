package com.example.ghostzilla.database.room

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LocalRepository @Inject constructor
    (private val cryptoDao: CryptoDao) {


}