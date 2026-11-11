package com.example.spaceexplorer.di

import com.example.spaceexplorer.data.local.database.feed.FeedDao
import com.example.spaceexplorer.data.local.datastore.SettingsDataStore
import com.example.spaceexplorer.data.repository.SettingsRepositoryImpl
import com.example.spaceexplorer.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(
        settingsDataStore: SettingsDataStore,
        dao: FeedDao
    ): SettingsRepository =
        SettingsRepositoryImpl(settingsDataStore, dao)
}