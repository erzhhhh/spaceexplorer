package com.example.spaceexplorer.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.repository.FavoritesListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class FavoritesListViewModel @Inject constructor(
    private val favoriteListRepository: FavoritesListRepository
) : ViewModel() {

    val favoriteArticlesFlow: Flow<PagingData<FeedArticle>> =
        favoriteListRepository.favoriteArticlesFlow
            .cachedIn(viewModelScope)
}

sealed interface FavoritesListState {
    data object Loading : FavoritesListState
    data class Loaded(val favoriteList: List<FeedArticle>) : FavoritesListState
    data class Error(val errorMessage: String) : FavoritesListState
}