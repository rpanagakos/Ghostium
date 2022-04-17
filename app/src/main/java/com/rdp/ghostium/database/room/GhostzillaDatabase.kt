package com.rdp.ghostium.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rdp.ghostium.database.room.articles.ArticlesDao
import com.rdp.ghostium.database.room.cryptos.CryptoDao
import com.rdp.ghostium.database.room.cryptos.TrendingDao
import com.rdp.ghostium.database.room.searches.SearchesDao
import com.rdp.ghostium.models.CryptoItemDB
import com.rdp.ghostium.models.coingecko.tredings.TredingCoins
import com.rdp.ghostium.models.guardian.Article
import com.rdp.ghostium.models.settings.RecentlyItem

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