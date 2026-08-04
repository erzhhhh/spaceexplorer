package com.example.spaceexplorer.ui.feed.detail

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.spaceexplorer.R
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.ui.components.FavoriteButton
import com.example.spaceexplorer.ui.components.FullScreenError
import com.example.spaceexplorer.ui.components.FullScreenLoading

@Composable
fun FeedArticleDetailScreen(
    viewModel: FeedArticleDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    // First, we retrieve information from the database. If the database has no info (a deep link came in), then we go to the internet.
    val stateCompose: FeedArticleState by viewModel.stateFlow.collectAsStateWithLifecycle()

    //    stateCompose is read every time through the delegate's getValue() — the compiler can't guarantee
    //    that between the line is FeedArticleState.Error -> and the next line stateCompose.errorMessage
    //    the value won't change to something else. So without saving it into a separate val, the smart
    //    cast doesn't kick in, and the compiler requires a manual as.
    //    When you write when (val state = stateCompose), state is now a regular local immutable val —
    //    the compiler is guaranteed to know it won't change, and the smart cast works normally.
    when (val feedArticleState = stateCompose) {
        // Loading is a single instance, a ready-made object. That's why in `when` it can be compared
        // directly using `==` (which is what happens without `is`).
        FeedArticleState.Loading -> FullScreenLoading()

        // This is not a value, but a class (a template for creation). "Loaded" can't be used as a value — it requires constructor arguments,
        // and you don't have a specific article to provide. `is` here means: "check that stateCompose is an instance of the Loaded class,
        // regardless of what data is inside it (article)."
        is FeedArticleState.Error -> FullScreenError(
            errorMessage = feedArticleState.errorMessage,
            // TODO process retry click
            onRetry = {})

        is FeedArticleState.Loaded -> ArticleDetailsScreen(
            article = feedArticleState.article,
            onBackClick = onBackClick,
            onFavoriteClick = {
                viewModel.saveFavoriteArticle(
                    feedArticleState.article
                )
            })
    }
}

@Composable
private fun ArticleDetailsScreen(
    modifier: Modifier = Modifier,
    article: FeedArticle,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = article.title,
                    modifier = Modifier
                        .matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color.Transparent,
                                    0.5f to Color.Transparent,
                                    1.0f to Color.Black.copy(alpha = 0.8f)
                                )
                            )
                        )
                )
                IconButton(
                    onClick = {
                        onBackClick()
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                FavoriteButton(
                    isFavorite = article.isFavorite,
                    onFavoriteClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                )
                Text(
                    text = article.title,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = article.newsSite,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = article.publishedAt,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.Gray
                )
                Text(
                    text = article.summary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            val context = LocalContext.current

            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.3f)),
                onClick = {
                    val intent = CustomTabsIntent.Builder()
                        .build()
                    intent.launchUrl(context, article.url.toUri())
                }
            ) {
                Text(
                    text = stringResource(R.string.read_in_full)
                )
            }
        }
    }
}