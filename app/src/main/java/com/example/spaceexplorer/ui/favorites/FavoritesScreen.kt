package com.example.spaceexplorer.ui.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.ui.components.FeedArticleCard
import com.example.spaceexplorer.ui.components.FullScreenError
import com.example.spaceexplorer.ui.components.FullScreenLoading

@Composable
fun FavoritesScreen(
    viewModel: FavoritesListViewModel,
    modifier: Modifier = Modifier
) {

    val uiState by viewModel.stateFlow.collectAsStateWithLifecycle()

    when (val state = uiState) {
        FavoritesListState.Loading -> FullScreenLoading()
        is FavoritesListState.Error -> FullScreenError(
            errorMessage = state.errorMessage,
            // TODO process retry click
            onRetry = { }
        )

        is FavoritesListState.Loaded -> FavoritesListScreen(
            favoriteList = state.favoriteList,
            modifier = modifier,
        )
    }
}

@Composable
fun FavoritesListScreen(favoriteList: List<FeedArticle>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier,
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
        items(favoriteList) { article ->
            FeedArticleCard(
                article = article,
                // TODO on article click
                onArticleClick = {}
            )
        }
    }
}