package com.example.spaceexplorer.ui.feed.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexplorer.FeedArticleDetailRoute
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.repository.FeedArticleDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class FeedArticleDetailViewModel @Inject constructor(
    feedArticleDetailRepository: FeedArticleDetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val articleId: String = requireNotNull(
        savedStateHandle[FeedArticleDetailRoute.ARTICLE_ID_ARG]
    )

    val stateFlow: StateFlow<FeedArticleState> = feedArticleDetailRepository
        .getFeedArticle(articleId)
        .map<FeedArticle, FeedArticleState> { FeedArticleState.Loaded(it) }
        .catch { emit(FeedArticleState.Error(it.message ?: "Unknown error")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FeedArticleState.Loading
        )
}

sealed interface FeedArticleState {
    data object Loading : FeedArticleState
    data class Loaded(val article: FeedArticle) : FeedArticleState
    data class Error(val errorMessage: String) : FeedArticleState
}