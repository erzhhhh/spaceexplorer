package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.local.database.launch.LaunchDao
import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.data.mapper.toEntity
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.LaunchArticle
import com.example.spaceexplorer.domain.repository.LaunchDetailRepository
import com.example.spaceexplorer.domain.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LaunchDetailRepositoryImpl(
    private val api: SpaceExplorerApi,
    private val settingsRepository: SettingsRepository,
    private val launchDao: LaunchDao
) : LaunchDetailRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFeedArticle(articleId: String): Flow<LaunchArticle> {
        return settingsRepository.offlineCachingFlow
            .distinctUntilChanged()
            .flatMapLatest { isCachingEnabled ->
                if (isCachingEnabled) {
                    observeArticleWithRefresh(articleId)
                } else {
                    flow { emit(api.getLaunchArticle(articleId).toDomain()) }
                }
            }
    }

    private fun observeArticleWithRefresh(articleId: String): Flow<LaunchArticle> {
        return flow {
            coroutineScope {
                // subscribing to the article while refreshing it in parallel
                launch {
                    try {
                        val dto = api.getLaunchArticle(articleId)
                        launchDao.insertArticle(dto.toEntity())
                    } catch (e: Exception) {
                        // cannot refresh the article - working with old cache
                    }
                }

                emitAll(launchDao.getArticle(articleId).map { it.toDomain() })
            }
        }
    }
}