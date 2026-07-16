package com.example.spaceexplorer.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.data.remote.dto.ArticleDto
import okio.IOException
import retrofit2.HttpException

class InMemoryFeedPagingSource(
    private val api: SpaceExplorerApi
) : PagingSource<String, ArticleDto>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ArticleDto> {
        return try {
            val cursor: String? = params.key

            val response = api.loadFeedCursor(publishedAtLt = cursor)

            val articles: List<ArticleDto> = response.results.orEmpty()

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

    override fun getRefreshKey(state: PagingState<String, ArticleDto>): String? {
        return null
    }
}