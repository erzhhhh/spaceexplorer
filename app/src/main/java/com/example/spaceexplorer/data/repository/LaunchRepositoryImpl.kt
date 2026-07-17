package com.example.spaceexplorer.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.spaceexplorer.data.local.LaunchRemoteMediator
import com.example.spaceexplorer.data.local.database.LaunchDao
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
    private val settingsRepository: SettingsRepository,
    private val launchDao: LaunchDao
) : LaunchRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val launchArticleFlow: Flow<PagingData<LaunchArticle>> =
        settingsRepository.offlineCachingFlow
            .distinctUntilChanged()
            .flatMapLatest { isCachingEnabled ->
                if (isCachingEnabled) {
                    cachedLaunchesPager()
                } else {
                    inMemoryLaunchesPager()
                }
            }

    @OptIn(ExperimentalPagingApi::class)
    fun cachedLaunchesPager(): Flow<PagingData<LaunchArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                initialLoadSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = LaunchRemoteMediator(api = spaceExplorerApi, dao = launchDao),
            pagingSourceFactory = { launchDao.getPagedArticles() }
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }
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
