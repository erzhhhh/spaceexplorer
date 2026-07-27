package com.example.spaceexplorer.domain.repository

import com.example.spaceexplorer.domain.model.FeedArticle
import kotlinx.coroutines.flow.Flow

interface FeedArticleDetailRepository {

    fun getFeedArticle(articleId: String): Flow<FeedArticle>
}