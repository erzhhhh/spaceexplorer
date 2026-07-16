package com.example.spaceexplorer.ui.feed

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
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.ui.components.ArticleCard
import com.example.spaceexplorer.ui.components.BottomErrorIndicator
import com.example.spaceexplorer.ui.components.BottomLoadingIndicator
import com.example.spaceexplorer.ui.components.FullScreenError
import com.example.spaceexplorer.ui.components.FullScreenLoading


@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    modifier: Modifier = Modifier
) {

    val lazyPagingItems: LazyPagingItems<FeedArticle> = viewModel.feed.collectAsLazyPagingItems()

    val isListEmpty = lazyPagingItems.itemCount == 0

    val refreshState = lazyPagingItems.loadState.refresh

    when {
        isListEmpty && refreshState is LoadState.Loading -> FullScreenLoading(modifier)
        isListEmpty && refreshState is LoadState.Error -> FullScreenError(
            modifier = modifier,
            errorMessage = refreshState.error.message ?: "Unknown error",
            onRetry = { lazyPagingItems.retry() })

        else -> FeedList(modifier = modifier, lazyPagingItems = lazyPagingItems)
    }
}

@Composable
fun FeedList(
    modifier: Modifier = Modifier,
    lazyPagingItems: LazyPagingItems<FeedArticle>
) {

    @OptIn(ExperimentalMaterial3Api::class)
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
                key = lazyPagingItems.itemKey { it.id },
            ) { index ->
                val article = lazyPagingItems[index]
                if (article != null) {
                    ArticleCard(article)
                }
            }

            // shows on the bottom of the list
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
                                onRetry = { lazyPagingItems.retry() })
                        }
                    }
                }
            }
        }
    }
}
