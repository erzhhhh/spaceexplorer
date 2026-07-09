package com.example.spaceexplorer.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexplorer.domain.model.Article
import com.example.spaceexplorer.domain.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedRepository: FeedRepository) : ViewModel() {

    val feed: StateFlow<FeedState> = feedRepository.articlesFlow
        .map<List<Article>, FeedState> { articles -> FeedState.Success(articles) }
        .catch { emit(FeedState.Error(it.message ?: "Unknown error")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FeedState.Loading
        )

    private val _errorEvents = Channel<String>()
    val errorEvents: Flow<String> = _errorEvents.receiveAsFlow()

    init {
        refreshFeed()
    }

    fun refreshFeed() {
        viewModelScope.launch {
            try {
                feedRepository.refreshArticles()
            } catch (e: Exception) {
                _errorEvents.send(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class FeedState {
    object Loading : FeedState()
    data class Success(val articles: List<Article>) : FeedState()
    data class Error(val message: String) : FeedState()
}