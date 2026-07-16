package com.example.spaceexplorer.domain.repository

import com.example.spaceexplorer.domain.model.FeedArticle

interface LaunchRepository {

    suspend fun getLaunches() : List<FeedArticle>
}