package com.example.spaceexplorer.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.data.remote.dto.LaunchArticleDto
import okio.IOException
import retrofit2.HttpException

class InMemoryLaunchPagingSource(
    private val api: SpaceExplorerApi
) : PagingSource<String, LaunchArticleDto>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, LaunchArticleDto> {
        return try {
            val cursor: String? = params.key
            val response = api.loadLaunchCursor(publishedAtLt = cursor)

            val launchArticles: List<LaunchArticleDto> = response.results.orEmpty()
            val nextKey: String? = launchArticles.lastOrNull()?.publishedAt

            LoadResult.Page(
                data = launchArticles,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: HttpException) {
            LoadResult.Error(throwable = e)
        } catch (e: IOException) {
            LoadResult.Error(throwable = e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, LaunchArticleDto>): String? {
        return null
    }
}