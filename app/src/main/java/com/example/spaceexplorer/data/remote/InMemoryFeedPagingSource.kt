package com.example.spaceexplorer.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.data.remote.dto.FeedArticleDto
import okio.IOException
import retrofit2.HttpException

class InMemoryFeedPagingSource(
    private val api: SpaceExplorerApi
) : PagingSource<String, FeedArticleDto>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, FeedArticleDto> {
        return try {
            val cursor: String? = params.key

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

    override fun getRefreshKey(state: PagingState<String, FeedArticleDto>): String? {
        return null
    }
}