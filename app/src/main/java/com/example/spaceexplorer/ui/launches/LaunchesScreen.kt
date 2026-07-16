package com.example.spaceexplorer.ui.launches

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.ui.components.ArticleCard

@Composable
fun LaunchesScreen(viewModel: LaunchesViewModel) {

    val uiState by viewModel.launches.collectAsState()

    when (val state = uiState) {
        LaunchesState.Loading -> LaunchLoadingScreen()
        is LaunchesState.Success -> LaunchesScreen(state.launches)
        is LaunchesState.Error -> LaunchErrorScreen(state.message)
    }
}

@Composable
fun LaunchesScreen(articles: List<FeedArticle>, modifier: Modifier = Modifier) {
    Scaffold() { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
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
fun LaunchLoadingScreen(modifier: Modifier = Modifier) {
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
fun LaunchErrorScreen(message: String) {

}