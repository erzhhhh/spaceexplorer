package com.example.spaceexplorer.ui.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.spaceexplorer.domain.model.Article
import com.example.spaceexplorer.domain.model.Author

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
fun ArticleCard(
    article: Article,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = article.title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Composable
fun ErrorScreen(message: String) {

}

@Preview(
    showBackground = true,
)
@Composable
fun ArticleCardPreview() {
    ArticleCard(
        article = Article(
            id = 1,
            title = "Title",
            authors = listOf(
                Author(
                    name = "Author 1"
                ),
                Author(
                    name = "Author 2"
                )
            ),
            url = "https://example.com",
            imageUrl = "https://developer.android.com/static/develop/ui/compose/images/tooling-preview-live-edit.gif",
            newsSite = "SpaceX",
            summary = "Summary",
            publishedAt = "2023-05-01T12:00:00Z",
        )
    )
}
