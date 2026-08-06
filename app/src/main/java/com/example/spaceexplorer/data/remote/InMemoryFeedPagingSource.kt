package com.example.spaceexplorer.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.data.remote.dto.FeedArticleDto
import okio.IOException
import retrofit2.HttpException
import kotlin.time.Instant

class InMemoryFeedPagingSource(
    private val api: SpaceExplorerApi
) : PagingSource<Instant, FeedArticleDto>() {

    override suspend fun load(params: LoadParams<Instant>): LoadResult<Instant, FeedArticleDto> {
        return try {
            val cursor: Instant? = params.key

            val response = api.loadFeedCursor(publishedAtLt = cursor)

            val articles: List<FeedArticleDto> = response.results.orEmpty()

            val nextKey = articles.lastOrNull()?.publishedAt.takeIf { articles.isNotEmpty() }

            LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(
                throwable = e
            )
        } catch (e: HttpException) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Instant, FeedArticleDto>): Instant? {
        return null
    }
}