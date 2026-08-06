package com.example.spaceexplorer.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spaceexplorer.data.local.database.favorites.FavoriteArticleEntity
import com.example.spaceexplorer.data.local.database.favorites.FavoritesDao
import com.example.spaceexplorer.data.local.database.feed.FeedArticleEntity
import com.example.spaceexplorer.data.local.database.feed.FeedDao
import com.example.spaceexplorer.data.local.database.launch.LaunchArticleEntity
import com.example.spaceexplorer.data.local.database.launch.LaunchDao

@Database(
    entities = [FeedArticleEntity::class, LaunchArticleEntity::class, FavoriteArticleEntity::class],
    version = 3
)
@TypeConverters(InstantConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun feedDao(): FeedDao

    abstract fun launchDao(): LaunchDao

    abstract fun favoritesDao(): FavoritesDao
}