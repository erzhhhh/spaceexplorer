package com.example.spaceexplorer.data.remote.api

import com.example.spaceexplorer.data.remote.dto.FeedDto
import com.example.spaceexplorer.data.remote.dto.LaunchDto
import retrofit2.http.GET

interface SpaceExplorerApi {

    @GET("articles")
    suspend fun loadFeed() : FeedDto

    @GET("reports")
    suspend fun loadLaunch() : LaunchDto
}