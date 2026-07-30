package com.example.spaceexplorer.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchDao {

    @Query("SELECT * FROM launch_articles ORDER BY publishedAt DESC")
    fun getPagedArticles(): PagingSource<Int, LaunchArticleEntity>

    @Query("SELECT * FROM launch_articles WHERE id = :id")
    fun getArticle(id: String): Flow<LaunchArticleEntity>

    @Upsert
    suspend fun insertArticles(articles: List<LaunchArticleEntity>)

    @Upsert
    suspend fun insertArticle(article: LaunchArticleEntity)

    @Query("DELETE FROM launch_articles")
    suspend fun deleteAllArticles()
}