package com.example.spaceexplorer.ui.launches

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.spaceexplorer.domain.model.Author
import com.example.spaceexplorer.domain.model.LaunchArticle

@Composable
fun LaunchArticleCard(
    article: LaunchArticle,
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

@Preview(
    showBackground = true,
)
@Composable
fun LaunchArticleCardPreview() {
    LaunchArticleCard(
        article = LaunchArticle(
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
