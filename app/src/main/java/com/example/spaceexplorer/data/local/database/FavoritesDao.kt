package com.example.spaceexplorer.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FavoritesDao {

    @Upsert
    suspend fun insertArticle(article: FavoriteArticleEntity)

    @Query("SELECT * FROM favorite_articles ORDER BY publishedAt DESC")
    fun getFavoriteArticles(): PagingSource<Int, FavoriteArticleEntity>
}