package com.example.spaceexplorer.ui.launches.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexplorer.LaunchArticleDetailRoute
import com.example.spaceexplorer.domain.model.LaunchArticle
import com.example.spaceexplorer.domain.repository.LaunchDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class LaunchArticleDetailViewModel @Inject constructor(
    launchDetailRepository: LaunchDetailRepository,
    stateHandle: SavedStateHandle
) : ViewModel() {

    val stateFlow: StateFlow<LaunchArticleState> =
        launchDetailRepository.getFeedArticle(requireNotNull(stateHandle[LaunchArticleDetailRoute.ARTICLE_ID_ARG]))
            .map { LaunchArticleState.Loaded(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = LaunchArticleState.Loading,
            )
}

sealed interface LaunchArticleState {
    data object Loading : LaunchArticleState
    data class Loaded(val article: LaunchArticle) : LaunchArticleState
    data class Error(val errorMessage: String) : LaunchArticleState
}