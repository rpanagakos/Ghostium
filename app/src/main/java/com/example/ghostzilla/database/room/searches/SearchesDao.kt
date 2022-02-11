package com.example.ghostzilla.database.room.searches

import androidx.room.*
import com.example.ghostzilla.models.settings.RecentlyItem
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchesDao {

    @Query("SELECT * FROM searches_table ORDER BY searchedText ASC")
    fun getAllSearches(): Flow<MutableList<RecentlyItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearch(cryptoName: RecentlyItem)

    @Delete
    suspend fun deleteRecentSearch(cryptoName: RecentlyItem)
}