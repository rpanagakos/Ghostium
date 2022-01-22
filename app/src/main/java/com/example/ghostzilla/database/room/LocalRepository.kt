package com.example.ghostzilla.database.room

import com.example.ghostzilla.models.coingecko.CryptoItem
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class LocalRepository @Inject constructor
    (private val cryptoDao: CryptoDao) {

    fun fetchFavouriteCryptos(): Flow<MutableList<CryptoItem>> {
        return cryptoDao.getAllCryptos()
    }

    suspend fun insertFavouriteCrypto(crypto: CryptoItem) {
        cryptoDao.addCrypto(crypto)
    }

    suspend fun deleteAllFavourites() {
        cryptoDao.deleteAllCryptos()
    }

    suspend fun deleteSpecificCryptos(list: List<String>) {
        cryptoDao.deleteListOfCryptos(list)
    }

    suspend fun deleteCrypto(crypto: CryptoItem) {
        return cryptoDao.deleteLocation(crypto)
    }

    fun isFavourite(id: String): Boolean {
        return cryptoDao.isCryptoExist(id)
    }

}