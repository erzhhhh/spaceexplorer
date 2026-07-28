package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.local.database.FavoritesDao
import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.repository.FavoritesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesListRepositoryImpl @Inject constructor(
    private val dao: FavoritesDao
) : FavoritesListRepository {

    override fun getFavoriteList(): Flow<List<FeedArticle>> {
        return dao.getFavoriteArticles()
            .map { list -> list.map { favoriteEntity -> favoriteEntity.toDomain() } }
    }
}