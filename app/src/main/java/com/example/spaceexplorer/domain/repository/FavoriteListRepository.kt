package com.example.spaceexplorer.domain.repository

import androidx.paging.PagingData
import com.example.spaceexplorer.domain.model.FeedArticle
import kotlinx.coroutines.flow.Flow

interface FavoritesListRepository {

    val favoriteArticlesFlow: Flow<PagingData<FeedArticle>>
}