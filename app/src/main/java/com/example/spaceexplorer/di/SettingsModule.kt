package com.example.spaceexplorer.di

import com.example.spaceexplorer.data.local.database.FeedDao
import com.example.spaceexplorer.data.local.datastore.SettingsDataStore
import com.example.spaceexplorer.data.repository.SettingsRepositoryImpl
import com.example.spaceexplorer.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class SettingsModule {

    @Provides
    @ActivityRetainedScoped
    fun provideSettingsRepository(
        settingsDataStore: SettingsDataStore,
        dao: FeedDao
    ): SettingsRepository =
        SettingsRepositoryImpl(settingsDataStore, dao)
}