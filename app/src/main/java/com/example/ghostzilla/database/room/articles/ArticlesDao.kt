package com.example.ghostzilla.database.room.articles

import androidx.room.*
import com.example.ghostzilla.models.guardian.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {

    @Query("SELECT * FROM articles_table ORDER BY id ASC")
    fun getAllArticles(): Flow<MutableList<Article>>

    @Query("DELETE FROM articles_table")
    suspend fun deleteAllArticles()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT EXISTS(SELECT * FROM articles_table WHERE id = :id)")
    fun isArticleExist(id: String): Boolean
}