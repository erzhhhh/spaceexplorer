package com.example.spaceexplorer.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.repository.FavoritesListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class FavoritesListViewModel @Inject constructor(
    private val favoriteListRepository: FavoritesListRepository
) : ViewModel() {

    val stateFlow = favoriteListRepository
        .getFavoriteList()
        .map<List<FeedArticle>, FavoritesListState> { FavoritesListState.Loaded(it) }
        .catch { emit(FavoritesListState.Error(it.message ?: "Unknown error")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavoritesListState.Loading
        )
}

sealed interface FavoritesListState {
    data object Loading : FavoritesListState
    data class Loaded(val favoriteList: List<FeedArticle>) : FavoritesListState
    data class Error(val errorMessage: String) : FavoritesListState
}