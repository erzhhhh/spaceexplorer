package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.local.database.FeedDao
import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.repository.FeedArticleDetailRepository
import com.example.spaceexplorer.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FeedArticleDetailRepositoryImpl(
    private val api: SpaceExplorerApi,
    private val dao: FeedDao,
    private val settingsRepository: SettingsRepository
) : FeedArticleDetailRepository {

    override fun getFeedArticle(articleId: String): Flow<FeedArticle> {
        return dao.getArticle(articleId)
            .map { it.toDomain() }
    }
}