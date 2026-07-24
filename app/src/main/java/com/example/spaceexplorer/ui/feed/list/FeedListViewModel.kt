package com.example.spaceexplorer.ui.feed.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FeedListViewModel @Inject constructor(feedRepository: FeedRepository) : ViewModel() {

    val feed: Flow<PagingData<FeedArticle>> = feedRepository.feedArticlesFlow
        .cachedIn(viewModelScope)
}