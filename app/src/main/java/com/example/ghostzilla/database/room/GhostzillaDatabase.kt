package com.example.ghostzilla.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ghostzilla.database.room.articles.ArticlesDao
import com.example.ghostzilla.database.room.cryptos.CryptoDao
import com.example.ghostzilla.database.room.cryptos.TrendingDao
import com.example.ghostzilla.database.room.searches.SearchesDao
import com.example.ghostzilla.models.CryptoItemDB
import com.example.ghostzilla.models.coingecko.tredings.TredingCoins
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.models.settings.RecentlyItem

@Database(
    entities = [CryptoItemDB::class, RecentlyItem::class, TredingCoins::class, Article::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatabaseConverter::class)
abstract class GhostzillaDatabase : RoomDatabase() {

    abstract fun cryptoDao(): CryptoDao
    abstract fun searchDao(): SearchesDao
    abstract fun trendingDao(): TrendingDao
    abstract fun articlesDao(): ArticlesDao

}