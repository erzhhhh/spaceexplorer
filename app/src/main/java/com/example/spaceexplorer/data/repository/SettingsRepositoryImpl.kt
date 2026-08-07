package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.local.database.feed.FeedDao
import com.example.spaceexplorer.data.local.datastore.SettingsDataStore
import com.example.spaceexplorer.domain.repository.SettingsRepository
import com.example.spaceexplorer.ui.theme.ThemeMode
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    private val dao: FeedDao,
) : SettingsRepository {

    override val themeModeFlow: Flow<ThemeMode>
        get() = settingsDataStore.themeModeFlow

    override val offlineCachingFlow: Flow<Boolean>
        get() = settingsDataStore.offlineCachingFlow

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        settingsDataStore.setThemeMode(themeMode)
    }

    override suspend fun setOfflineCaching(enabled: Boolean) {
        if (!enabled) {
            dao.deleteAllArticles()
        }
        settingsDataStore.setOfflineCaching(enabled)
    }
}