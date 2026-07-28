package com.example.spaceexplorer.data.local.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Upsert
    suspend fun insertArticle(article: FavoriteArticleEntity)

    @Query("SELECT * FROM favorite_articles")
    fun getFavoriteArticles(): Flow<List<FavoriteArticleEntity>>
}