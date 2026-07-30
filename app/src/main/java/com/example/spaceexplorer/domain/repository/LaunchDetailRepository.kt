package com.example.spaceexplorer.domain.repository

import com.example.spaceexplorer.domain.model.LaunchArticle
import kotlinx.coroutines.flow.Flow

interface LaunchDetailRepository {

    fun getFeedArticle(articleId: String): Flow<LaunchArticle>
}