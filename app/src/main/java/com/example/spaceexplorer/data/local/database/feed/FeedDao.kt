package com.example.spaceexplorer.data.local.database.feed

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.spaceexplorer.data.local.database.feed.FeedArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedDao {

    @Query("SELECT * FROM feed_articles ORDER BY publishedAt DESC")
    fun getPagedArticles(): PagingSource<Int, FeedArticleEntity>

    @Query("SELECT * FROM feed_articles WHERE id = :articleId")
    fun getArticle(articleId: String): Flow<FeedArticleEntity>

    @Upsert
    suspend fun insertArticles(articles: List<FeedArticleEntity>)

    @Upsert
    suspend fun insertArticle(article: FeedArticleEntity)

    @Query("DELETE FROM feed_articles")
    suspend fun deleteAllArticles()
}