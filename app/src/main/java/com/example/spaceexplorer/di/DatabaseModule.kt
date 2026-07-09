package com.example.spaceexplorer.di

import android.content.Context
import androidx.room.Room
import com.example.spaceexplorer.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            context = applicationContext,
            klass = AppDatabase::class.java,
            name = "space_explorer_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(database: AppDatabase) = database.articleDao()
}