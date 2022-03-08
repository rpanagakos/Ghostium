package com.example.ghostzilla.database.room.cryptos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ghostzilla.models.coingecko.tredings.TredingCoins
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingDao {

    @Query("SELECT * FROM trendings_table")
    fun getAllTrendings(): Flow<MutableList<TredingCoins>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrendings(item: TredingCoins)
}