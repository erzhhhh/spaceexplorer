package com.example.spaceexplorer.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FeedArticleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): FeedDao
}