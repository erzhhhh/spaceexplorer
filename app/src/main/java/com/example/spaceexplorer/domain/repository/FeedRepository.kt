package com.example.spaceexplorer.domain.repository

import androidx.paging.PagingData
import com.example.spaceexplorer.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    val articlesFlow: Flow<PagingData<Article>>
}