package com.example.spaceexplorer.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface LaunchDao {

    @Query("SELECT * FROM launch_articles ORDER BY publishedAt DESC")
    fun getPagedArticles(): PagingSource<Int, LaunchArticleEntity>

    @Upsert
    suspend fun insertArticles(articles: List<LaunchArticleEntity>)

    @Query("DELETE FROM launch_articles")
    suspend fun deleteAllArticles()
}