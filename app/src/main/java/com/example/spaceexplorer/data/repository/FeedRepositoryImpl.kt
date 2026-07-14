package com.example.spaceexplorer.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spaceexplorer.data.remote.InMemoryArticlesPagingSource
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.Article
import com.example.spaceexplorer.domain.repository.FeedRepository
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class FeedRepositoryImpl @Inject constructor(
    private val api: SpaceExplorerApi
) : FeedRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val articlesFlow: Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { InMemoryArticlesPagingSource(api) }
        ).flow
}