package com.example.spaceexplorer.data.remote.api

import com.example.spaceexplorer.data.remote.dto.FeedDto
import com.example.spaceexplorer.data.remote.dto.LaunchDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceExplorerApi {

    @GET("articles")
    suspend fun loadFeedCursor(
        @Query("published_at_lt") publishedAtLt: String?
    ): FeedDto

    @GET("reports")
    suspend fun loadLaunch(): LaunchDto
}