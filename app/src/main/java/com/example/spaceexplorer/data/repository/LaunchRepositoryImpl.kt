package com.example.spaceexplorer.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.data.remote.InMemoryLaunchPagingSource
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.LaunchArticle
import com.example.spaceexplorer.domain.repository.LaunchRepository
import com.example.spaceexplorer.domain.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LaunchRepositoryImpl @Inject constructor(
    private val spaceExplorerApi: SpaceExplorerApi,
    private val settingsRepository: SettingsRepository
) : LaunchRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val launchArticleFlow: Flow<PagingData<LaunchArticle>> =
        settingsRepository.offlineCachingFlow
            .distinctUntilChanged()
            .flatMapLatest { isCachingEnabled ->
                inMemoryLaunchesPager()
            }

    fun inMemoryLaunchesPager(): Flow<PagingData<LaunchArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                initialLoadSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { InMemoryLaunchPagingSource(spaceExplorerApi) }
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }
    }
}
