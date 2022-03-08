package com.example.ghostzilla.database.room.cryptos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ghostzilla.models.coingecko.tredings.TredingCoins

@Dao
interface TrendingDao {

    @Query("SELECT * FROM trendings_table")
    suspend fun getAllTrendings(): TredingCoins

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrendings(item: TredingCoins)
}