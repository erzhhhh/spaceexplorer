package com.example.spaceexplorer.data.remote.api

import com.example.spaceexplorer.data.remote.dto.FeedArticleDto
import com.example.spaceexplorer.data.remote.dto.FeedDto
import com.example.spaceexplorer.data.remote.dto.LaunchArticleDto
import com.example.spaceexplorer.data.remote.dto.LaunchDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpaceExplorerApi {

    @GET("articles")
    suspend fun loadFeedCursor(
        @Query("published_at_lt") publishedAtLt: String?
    ): FeedDto

    @GET("articles/{id}")
    suspend fun getFeedArticle(
        @Path("id") id: String
    ): FeedArticleDto

    @GET("reports")
    suspend fun loadLaunchCursor(
        @Query("published_at_lt") publishedAtLt: String?
    ): LaunchDto

    @GET("reports/{id}")
    suspend fun getLaunchArticle(
        @Path("id") id: String
    ): LaunchArticleDto
}