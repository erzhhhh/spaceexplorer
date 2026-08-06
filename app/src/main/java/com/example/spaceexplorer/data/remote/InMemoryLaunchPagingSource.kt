package com.example.spaceexplorer.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.data.remote.dto.LaunchArticleDto
import okio.IOException
import retrofit2.HttpException
import kotlin.time.Instant

class InMemoryLaunchPagingSource(
    private val api: SpaceExplorerApi
) : PagingSource<Instant, LaunchArticleDto>() {

    override suspend fun load(params: LoadParams<Instant>): LoadResult<Instant, LaunchArticleDto> {
        return try {
            val cursor: Instant? = params.key
            val response = api.loadLaunchCursor(publishedAtLt = cursor)

            val launchArticles: List<LaunchArticleDto> = response.results.orEmpty()
            val nextKey: Instant? = launchArticles.lastOrNull()?.publishedAt

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

    override fun getRefreshKey(state: PagingState<Instant, LaunchArticleDto>): Instant? {
        return null
    }
}