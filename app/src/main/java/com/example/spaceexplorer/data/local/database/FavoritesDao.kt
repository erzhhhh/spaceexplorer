package com.example.spaceexplorer.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Upsert
    suspend fun insertArticle(article: FavoriteArticleEntity)

    @Query("DELETE FROM favorite_articles WHERE id = :articleId")
    suspend fun deleteArticle(articleId: Int)

    @Query("SELECT * FROM favorite_articles ORDER BY publishedAt DESC")
    fun getFavoriteArticles(): PagingSource<Int, FavoriteArticleEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_articles WHERE id = :articleId)")
    fun isFavorite(articleId: String): Flow<Boolean>
}