package com.example.spaceexplorer.domain.repository

import androidx.paging.PagingData
import com.example.spaceexplorer.domain.model.LaunchArticle
import kotlinx.coroutines.flow.Flow

interface LaunchRepository {

    val launchArticleFlow: Flow<PagingData<LaunchArticle>>
}