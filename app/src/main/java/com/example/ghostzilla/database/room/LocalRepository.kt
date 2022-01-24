package com.example.ghostzilla.database.room

import com.example.ghostzilla.models.CryptoItemDB
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class LocalRepository @Inject constructor(private val cryptoDao: CryptoDao) {

    fun fetchFavouriteCryptos(): Flow<MutableList<CryptoItemDB>> {
        return cryptoDao.getAllCryptos()
    }

    suspend fun insertFavouriteCrypto(crypto: CryptoItemDB) {
        cryptoDao.addCrypto(crypto)
    }

    suspend fun deleteAllFavourites() {
        cryptoDao.deleteAllCryptos()
    }

    suspend fun deleteSpecificCryptos(list: List<String>) {
        cryptoDao.deleteListOfCryptos(list)
    }

    suspend fun deleteCrypto(crypto: CryptoItemDB) {
        return cryptoDao.deleteLocation(crypto)
    }

    fun isFavourite(id: String): Boolean {
        return cryptoDao.isCryptoExist(id)
    }

}