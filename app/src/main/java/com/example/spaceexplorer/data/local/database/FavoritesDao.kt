package com.example.spaceexplorer.data.local.database

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface FavoritesDao {

    @Upsert
    suspend fun insertArticle(article: FavoriteArticleEntity)
}