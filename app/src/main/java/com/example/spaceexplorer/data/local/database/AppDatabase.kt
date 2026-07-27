package com.example.spaceexplorer.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FeedArticleEntity::class, LaunchArticleEntity::class, FavoriteArticleEntity::class],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun feedDao(): FeedDao

    abstract fun launchDao(): LaunchDao

    abstract fun favoritesDao(): FavoritesDao
}