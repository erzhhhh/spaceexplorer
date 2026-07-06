package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.Article
import com.example.spaceexplorer.domain.repository.FeedRepository
import jakarta.inject.Inject

class FeedRepositoryImpl @Inject constructor(private val api: SpaceExplorerApi) : FeedRepository {

    override suspend fun getArticles(): List<Article> {
        return api.loadFeed().results?.map { it.toDomain() }.orEmpty()
    }
}