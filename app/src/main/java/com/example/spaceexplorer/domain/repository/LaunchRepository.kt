package com.example.spaceexplorer.domain.repository

import com.example.spaceexplorer.domain.model.Article

interface LaunchRepository {

    suspend fun getLaunches() : List<Article>
}