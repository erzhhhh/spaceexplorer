package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.Article
import com.example.spaceexplorer.domain.repository.LaunchRepository
import javax.inject.Inject

class LaunchRepositoryImpl @Inject constructor(private val spaceExplorerApi: SpaceExplorerApi) :
    LaunchRepository {

    override suspend fun getLaunches(): List<Article> {
        return spaceExplorerApi.loadLaunch().results?.map { it.toDomain() }.orEmpty()
    }
}
