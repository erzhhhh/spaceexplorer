package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.local.database.ArticleDao
import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.data.mapper.toEntity
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.Article
import com.example.spaceexplorer.domain.repository.FeedRepository
import com.example.spaceexplorer.domain.repository.SettingsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class FeedRepositoryImpl @Inject constructor(
    private val api: SpaceExplorerApi,
    private val dao: ArticleDao,
    private val settingsRepository: SettingsRepository
) : FeedRepository {

    // MutableStateFlow - articles will be cached in RAM
    private val _inMemoryArticles = MutableStateFlow<List<Article>>(emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    override val articlesFlow: Flow<List<Article>> = settingsRepository.offlineCachingFlow
        .flatMapLatest { cachingEnabled ->
            if (cachingEnabled) {
                val currentArticles: List<Article> = _inMemoryArticles.value
                if (currentArticles.isNotEmpty()) {
                    dao.insertArticles(currentArticles.map { it.toEntity() })
                }

                return@flatMapLatest dao.getAllArticles()
                    .map { entities -> entities.map { it.toDomain() } }
            } else {
                return@flatMapLatest _inMemoryArticles
            }
        }

    override suspend fun refreshArticles() {
        val articlesDto = api.loadFeed().results.orEmpty()
        val entities = articlesDto.map { it.toEntity() }

        val cachingEnabled = settingsRepository.offlineCachingFlow.first()
        if (cachingEnabled) {
            dao.deleteAllArticles()
            dao.insertArticles(entities)
        } else {
            _inMemoryArticles.value = entities.map { it.toDomain() }
        }
    }
}