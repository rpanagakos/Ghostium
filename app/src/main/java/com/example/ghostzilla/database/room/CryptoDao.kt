package com.example.ghostzilla.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ghostzilla.models.coingecko.CryptoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {

    @Query("SELECT * FROM cryptos_table ORDER BY id ASC")
    fun getAllCryptos(): Flow<List<CryptoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCrypto(cryptoItem: CryptoItem)

    @Query("DELETE FROM cryptos_table")
    suspend fun  deleteAllCryptos()

    @Query("DELETE FROM cryptos_table WHERE id IN (:cryptosId)")
    suspend fun deleteListOfCryptos(cryptosId : List<String>)
}