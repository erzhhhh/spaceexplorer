package com.example.spaceexplorer.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    private object PreferenceKeys {
        val DARK_MODE: Preferences.Key<Boolean> = booleanPreferencesKey("dark_mode")
        val OFFLINE_CACHING: Preferences.Key<Boolean> = booleanPreferencesKey("offline_caching")
    }

    val darkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferenceKeys.DARK_MODE] ?: false
        }

    val offlineCachingFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PreferenceKeys.OFFLINE_CACHING] ?: false
        }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.DARK_MODE] = enabled
        }
    }

    suspend fun setOfflineCaching(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.OFFLINE_CACHING] = enabled
        }
    }
}