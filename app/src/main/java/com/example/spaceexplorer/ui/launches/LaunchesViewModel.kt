package com.example.spaceexplorer.ui.launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.repository.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val launchesRepository: LaunchRepository
) : ViewModel() {

    private val _launches = MutableStateFlow<LaunchesState>(LaunchesState.Loading)
    val launches: StateFlow<LaunchesState> = _launches.asStateFlow()

    init {
        getLaunches()
    }

    fun getLaunches() {
        _launches.value = LaunchesState.Loading
        viewModelScope.launch {
            try {
                val launches = launchesRepository.getLaunches()
                _launches.value = LaunchesState.Success(launches)

                // TODO add CancellationException handler
            } catch (e: Exception) {
                _launches.value = LaunchesState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class LaunchesState {
    object Loading : LaunchesState()
    data class Success(val launches: List<FeedArticle>) : LaunchesState()
    data class Error(val message: String) : LaunchesState()
}