package com.example.spaceexplorer.data.remote.api

import com.example.spaceexplorer.data.remote.dto.FeedDto
import retrofit2.http.GET

interface SpaceExplorerApi {

    @GET("articles")
    suspend fun loadFeed() : FeedDto
}