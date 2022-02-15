package com.example.ghostzilla.database.room.searches

import androidx.room.*
import com.example.ghostzilla.models.settings.RecentlyItem

@Dao
interface SearchesDao {

    @Query("SELECT * FROM searches_table ORDER BY searchedText DESC")
    suspend fun getAllSearches(): MutableList<RecentlyItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearch(item: RecentlyItem)

    @Delete
    suspend fun deleteRecentSearch(item: RecentlyItem)
}