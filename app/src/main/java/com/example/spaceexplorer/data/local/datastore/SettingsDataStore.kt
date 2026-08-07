package com.example.spaceexplorer.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.spaceexplorer.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    private object PreferenceKeys {
        val THEME_MODE: Preferences.Key<String> = stringPreferencesKey("theme_mode")
        val OFFLINE_CACHING: Preferences.Key<Boolean> = booleanPreferencesKey("offline_caching")
    }

    val themeModeFlow: Flow<ThemeMode> = context.dataStore.data
        .map { preferences ->
            ThemeMode.fromName(preferences[PreferenceKeys.THEME_MODE])
        }

    val offlineCachingFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferenceKeys.OFFLINE_CACHING] ?: false
        }

    suspend fun setThemeMode(themeMode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.THEME_MODE] = themeMode.name
        }
    }

    suspend fun setOfflineCaching(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.OFFLINE_CACHING] = enabled
        }
    }
}