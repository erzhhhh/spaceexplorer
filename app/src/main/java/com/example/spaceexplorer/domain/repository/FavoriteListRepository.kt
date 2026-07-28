package com.example.spaceexplorer.domain.repository

import com.example.spaceexplorer.domain.model.FeedArticle
import kotlinx.coroutines.flow.Flow

interface FavoritesListRepository {

    fun getFavoriteList(): Flow<List<FeedArticle>>
}