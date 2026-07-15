package com.example.spaceexplorer.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.Article
import okio.IOException
import retrofit2.HttpException

class InMemoryFeedPagingSource(
    private val api: SpaceExplorerApi
) : PagingSource<String, Article>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Article> {
        return try {
            val cursor: String? = params.key

            val response = api.loadFeedCursor(publishedAtLt = cursor)

            val articles: List<Article> = response.results.orEmpty().map { it.toDomain() }

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

    override fun getRefreshKey(state: PagingState<String, Article>): String? {
        return null
    }
}