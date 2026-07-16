package com.example.spaceexplorer.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.spaceexplorer.data.local.database.FeedDao
import com.example.spaceexplorer.data.local.database.FeedArticleEntity
import com.example.spaceexplorer.data.mapper.toEntity
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.data.remote.dto.FeedArticleDto
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class FeedRemoteMediator(
    private val api: SpaceExplorerApi,
    private val dao: FeedDao
) : RemoteMediator<Int, FeedArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, FeedArticleEntity>
    ): MediatorResult {

        return try {

            val cursor: String? = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {

                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(
                        endOfPaginationReached = false
                    )

                    lastItem.publishedAt
                }
            }

            val response = api.loadFeedCursor(publishedAtLt = cursor)

            val articles: List<FeedArticleDto> = response.results.orEmpty()
            val endOfPaginationReached = articles.isEmpty()

            if (loadType == LoadType.REFRESH) {
                dao.deleteAllArticles()
            }
            val entities = articles.map { it.toEntity() }
            dao.insertArticles(entities)

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}