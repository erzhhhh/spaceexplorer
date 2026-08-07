package com.example.spaceexplorer.domain.repository

import com.example.spaceexplorer.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val themeModeFlow: Flow<ThemeMode>

    val offlineCachingFlow: Flow<Boolean>

    suspend fun setThemeMode(themeMode: ThemeMode)

    suspend fun setOfflineCaching(enabled: Boolean)
}