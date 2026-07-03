package com.example.spaceexplorer.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexplorer.domain.model.Article
import com.example.spaceexplorer.domain.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val feedRepository: FeedRepository) : ViewModel() {

    private val _feed = MutableStateFlow<FeedState>(FeedState.Loading)
    val feed: StateFlow<FeedState> = _feed.asStateFlow()

    init {
        loadFeed()
    }

    fun loadFeed() {
        _feed.value = FeedState.Loading
        viewModelScope.launch {
            try {
                val articles = feedRepository.getArticles()
                _feed.value = FeedState.Success(articles)
            } catch (e: Exception) {
                _feed.value = FeedState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

sealed class FeedState {
    object Loading : FeedState()
    data class Success(val articles: List<Article>) : FeedState()
    data class Error(val message: String) : FeedState()
}