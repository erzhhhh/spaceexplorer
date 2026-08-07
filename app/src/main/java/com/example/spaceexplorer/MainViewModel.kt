package com.example.spaceexplorer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexplorer.domain.repository.SettingsRepository
import com.example.spaceexplorer.ui.theme.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val themeMode: StateFlow<ThemeMode?> = settingsRepository.themeModeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}