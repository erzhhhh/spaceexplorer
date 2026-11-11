package com.example.spaceexplorer.di

import com.example.spaceexplorer.data.local.database.favorites.FavoritesDao
import com.example.spaceexplorer.data.local.database.feed.FeedDao
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.data.repository.FeedDetailsRepositoryImpl
import com.example.spaceexplorer.data.repository.FeedRepositoryImpl
import com.example.spaceexplorer.domain.repository.FeedArticleDetailRepository
import com.example.spaceexplorer.domain.repository.FeedRepository
import com.example.spaceexplorer.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FeedModule {

    @Provides
    @Singleton
    fun provideFeedRepository(
        api: SpaceExplorerApi,
        dao: FeedDao,
        settingsRepository: SettingsRepository
    ): FeedRepository {
        return FeedRepositoryImpl(api = api, dao = dao, settingsRepository = settingsRepository)
    }

    @Provides
    @Singleton
    fun provideFeedArticleDetailRepository(
        api: SpaceExplorerApi,
        feedDao: FeedDao,
        favoritesDao: FavoritesDao,
        settingsRepository: SettingsRepository
    ): FeedArticleDetailRepository {
        return FeedDetailsRepositoryImpl(
            api = api,
            feedDao = feedDao,
            favoritesDao = favoritesDao,
            settingsRepository = settingsRepository
        )
    }
}