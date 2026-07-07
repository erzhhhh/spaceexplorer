package com.example.spaceexplorer.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val darkModeFlow: Flow<Boolean>

    val offlineCachingFlow: Flow<Boolean>

    suspend fun setDarkMode(enabled: Boolean)

    suspend fun setOfflineCaching(enabled: Boolean)
}