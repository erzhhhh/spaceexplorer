package com.example.spaceexplorer.ui.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spaceexplorer.domain.model.Article
import com.example.spaceexplorer.ui.components.ArticleCard

@Composable
fun FeedScreen(
    viewModel: FeedViewModel
) {

    val uiState: FeedState by viewModel.feed.collectAsStateWithLifecycle()

    when (val state = uiState) {
        FeedState.Loading -> LoadingScreen()
        is FeedState.Success -> FeedScreen(state.articles)
        is FeedState.Error -> ErrorScreen(state.message)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 8.dp
        )
    }
}

@Composable
fun FeedScreen(
    articles: List<Article>,
    modifier: Modifier = Modifier
) {
    Scaffold() { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = articles,
                key = { article -> article.id }
            ) { article ->
                ArticleCard(article)
            }
        }
    }
}

@Composable
fun ErrorScreen(message: String) {

}
