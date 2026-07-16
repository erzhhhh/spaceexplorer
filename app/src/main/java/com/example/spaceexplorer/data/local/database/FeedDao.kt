package com.example.spaceexplorer.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FeedDao {

    @Query("SELECT * FROM feed_articles ORDER BY publishedAt DESC")
    fun getPagedArticles(): PagingSource<Int, FeedArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<FeedArticleEntity>)

    @Query("DELETE FROM feed_articles")
    suspend fun deleteAllArticles()
}