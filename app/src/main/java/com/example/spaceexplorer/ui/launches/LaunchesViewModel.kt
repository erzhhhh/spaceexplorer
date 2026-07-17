package com.example.spaceexplorer.ui.launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.spaceexplorer.domain.model.LaunchArticle
import com.example.spaceexplorer.domain.repository.LaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val launchesRepository: LaunchRepository
) : ViewModel() {

    val launchArticles: Flow<PagingData<LaunchArticle>> = launchesRepository.launchArticleFlow
        .cachedIn(viewModelScope)
}