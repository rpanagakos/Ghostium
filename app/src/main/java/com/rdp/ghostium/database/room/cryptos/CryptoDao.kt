package com.rdp.ghostium.database.room.cryptos

import androidx.room.*
import com.rdp.ghostium.models.CryptoItemDB
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {

    @Query("SELECT * FROM cryptos_table ORDER BY id ASC")
    fun getAllCryptos(): Flow<MutableList<CryptoItemDB>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCrypto(cryptoItem: CryptoItemDB)

    @Query("DELETE FROM cryptos_table")
    suspend fun  deleteAllCryptos()

    @Query("DELETE FROM cryptos_table WHERE id IN (:cryptosId)")
    suspend fun deleteListOfCryptos(cryptosId : List<String>)

    @Delete
    suspend fun deleteLocation(cryptoItem: CryptoItemDB)

    @Query("SELECT EXISTS(SELECT * FROM cryptos_table WHERE id = :id)")
    fun isCryptoExist(id : String) : Boolean
}