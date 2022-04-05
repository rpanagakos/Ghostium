package com.example.ghostzilla.database.room

import com.example.ghostzilla.database.room.articles.ArticlesDao
import com.example.ghostzilla.database.room.cryptos.CryptoDao
import com.example.ghostzilla.database.room.cryptos.TrendingDao
import com.example.ghostzilla.database.room.searches.SearchesDao
import com.example.ghostzilla.models.CryptoItemDB
import com.example.ghostzilla.models.coingecko.tredings.TredingCoins
import com.example.ghostzilla.models.guardian.Article
import com.example.ghostzilla.models.settings.RecentlyItem
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class LocalRepository @Inject constructor(
    private val cryptoDao: CryptoDao,
    private val searchesDao: SearchesDao,
    private val trendingDao: TrendingDao,
    private val articlesDao: ArticlesDao
) {

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

    suspend fun fetchRecentlySearches(): MutableList<RecentlyItem> {
        return searchesDao.getAllSearches()
    }

    suspend fun deleteRecentItem(item: RecentlyItem) {
        searchesDao.deleteRecentSearch(item)
    }

    suspend fun insertRecentItem(item: RecentlyItem) {
        searchesDao.addSearch(item)
    }

    fun fetchTrendingCryptos(): Flow<MutableList<TredingCoins>> {
        return trendingDao.getAllTrendings()
    }

    suspend fun insertTrendingCoins(tredingCoins: TredingCoins) {
        trendingDao.addTrendings(tredingCoins)
    }

    fun fetchAllArticles(): Flow<MutableList<Article>> {
        return articlesDao.getAllArticles()
    }

    suspend fun deleteAllArticles() {
        articlesDao.deleteAllArticles()
    }

    suspend fun insertFavouriteArticle(article: Article) {
        articlesDao.addArticle(article)
    }

    suspend fun deleteSpecificArticles(list: List<String>) {
        articlesDao.deleteListOfArticles(list)
    }

    suspend fun deleteArticle(article: Article) {
        return articlesDao.deleteArticle(article)
    }

    fun isSavedArticle(id: String): Boolean {
        return articlesDao.isArticleExist(id)
    }

}