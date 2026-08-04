package com.example.spaceexplorer.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.ui.components.FeedArticleCard
import com.example.spaceexplorer.ui.components.FullScreenError
import com.example.spaceexplorer.ui.components.FullScreenLoading

@Composable
fun FavoritesScreen(
    viewModel: FavoritesListViewModel,
    modifier: Modifier = Modifier,
    onArticleClick: (Int) -> Unit
) {

    val lazyPagingItems: LazyPagingItems<FeedArticle> =
        viewModel.favoriteArticlesFlow.collectAsLazyPagingItems()

    // refresh status is only for the first-time loading or pull-to-refresh request
    when (val state = lazyPagingItems.loadState.refresh) {
        is LoadState.Loading -> FullScreenLoading()
        is LoadState.Error -> FullScreenError(
            errorMessage = state.error.message ?: "Unknown error",
            onRetry = { lazyPagingItems.retry() })

        else -> FavoritesListScreen(
            lazyPagingItems = lazyPagingItems, modifier = modifier, onArticleClick = onArticleClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesListScreen(
    lazyPagingItems: LazyPagingItems<FeedArticle>,
    modifier: Modifier = Modifier,
    onArticleClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier, contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = 16.dp + WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        ), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.id },
        ) { index ->
            val article = lazyPagingItems[index]
            if (article != null) {
                FeedArticleCard(
                    article = article,
                    onArticleClick = {
                        onArticleClick(article.id)
                    }
                )
            }
        }
    }
}