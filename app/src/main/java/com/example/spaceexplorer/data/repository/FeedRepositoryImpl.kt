package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.local.database.ArticleDao
import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.data.mapper.toEntity
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.Article
import com.example.spaceexplorer.domain.repository.FeedRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FeedRepositoryImpl @Inject constructor(
    private val api: SpaceExplorerApi,
    private val dao: ArticleDao
) : FeedRepository {

    override val articlesFlow: Flow<List<Article>> = dao.getAllArticles()
        .map { entities -> entities.map { it.toDomain() } }

    override suspend fun refreshArticles() {
        val articlesDto = api.loadFeed().results.orEmpty()
        val entities = articlesDto.map { it.toEntity() }
        dao.deleteAllArticles()
        dao.insertArticles(entities)
    }
}