package com.example.spaceexplorer.ui.launches

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.spaceexplorer.R
import com.example.spaceexplorer.domain.model.LaunchArticle
import com.example.spaceexplorer.ui.components.BottomErrorIndicator
import com.example.spaceexplorer.ui.components.BottomLoadingIndicator
import com.example.spaceexplorer.ui.components.FullScreenError
import com.example.spaceexplorer.ui.components.FullScreenLoading

@Composable
fun LaunchesScreen(
    viewModel: LaunchesViewModel,
    modifier: Modifier = Modifier
) {

    val lazyPagingItems: LazyPagingItems<LaunchArticle> =
        viewModel.launchArticles.collectAsLazyPagingItems()

    val isListEmpty = lazyPagingItems.itemCount == 0

    // refresh status is only when loading for the first time or pull-to-refresh
    val refreshState = lazyPagingItems.loadState.refresh

    when {
        isListEmpty && refreshState is LoadState.Loading -> FullScreenLoading()
        isListEmpty && refreshState is LoadState.Error -> FullScreenError(
            modifier = modifier,
            errorMessage = refreshState.error.message ?: "Unknown error",
            onRetry = { lazyPagingItems.retry() }
        )

        else -> LaunchList(modifier = Modifier, lazyPagingItems = lazyPagingItems)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchList(modifier: Modifier = Modifier, lazyPagingItems: LazyPagingItems<LaunchArticle>) {
    PullToRefreshBox(
        isRefreshing = lazyPagingItems.loadState.refresh is LoadState.Loading,
        onRefresh = { lazyPagingItems.refresh() },
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
                top = 16.dp + WindowInsets.statusBars
                    .asPaddingValues()
                    .calculateTopPadding()
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.id }
            ) { index ->
                val article = lazyPagingItems[index]
                if (article != null) {
                    LaunchArticleCard(article)
                }
            }

            // append status is only when loading more data on the bottom of the list
            // prepend status is only when loading more data at the top of the list
            when (val appendState = lazyPagingItems.loadState.append) {
                is LoadState.Loading -> item { BottomLoadingIndicator() }
                is LoadState.Error -> item {
                    BottomErrorIndicator(
                        appendState.error.message ?: "Unknown error",
                        onRetry = { lazyPagingItems.retry() }
                    )
                }

                is LoadState.NotLoading -> {
                    if (appendState.endOfPaginationReached) {
                        item {
                            BottomErrorIndicator(
                                stringResource(R.string.feed_no_more_articles),
                                onRetry = { lazyPagingItems.retry() }
                            )
                        }
                    }
                }
            }
        }
    }
}