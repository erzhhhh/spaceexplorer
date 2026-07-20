package com.example.spaceexplorer.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LaunchDao {

    @Query("SELECT * FROM launch_articles ORDER BY publishedAt DESC")
    fun getPagedArticles(): PagingSource<Int, LaunchArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<LaunchArticleEntity>)

    @Query("DELETE FROM launch_articles")
    suspend fun deleteAllArticles()
}