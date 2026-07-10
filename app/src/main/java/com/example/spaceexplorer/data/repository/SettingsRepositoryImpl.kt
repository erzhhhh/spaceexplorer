package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.local.database.ArticleDao
import com.example.spaceexplorer.data.local.datastore.SettingsDataStore
import com.example.spaceexplorer.domain.repository.SettingsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
    private val dao: ArticleDao,
) : SettingsRepository {

    override val darkModeFlow: Flow<Boolean>
        get() = settingsDataStore.darkModeFlow

    override val offlineCachingFlow: Flow<Boolean>
        get() = settingsDataStore.offlineCachingFlow

    override suspend fun setDarkMode(enabled: Boolean) {
        settingsDataStore.setDarkMode(enabled)
    }

    override suspend fun setOfflineCaching(enabled: Boolean) {
        if (!enabled){
            dao.deleteAllArticles()
        }
        settingsDataStore.setOfflineCaching(enabled)
    }
}