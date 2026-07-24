package com.example.spaceexplorer.ui.feed.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.ui.components.FullScreenError
import com.example.spaceexplorer.ui.components.FullScreenLoading

@Composable
fun FeedArticleDetailScreen(
    viewModel: FeedArticleDetailViewModel = hiltViewModel()
) {
    // Сначала достаем информацию из базы. Если в базе нет инфы (пришел диплинк), то идем в интернет

    val stateCompose: FeedArticleState by viewModel.stateFlow.collectAsStateWithLifecycle()

    // stateCompose технически каждый раз читается через getValue() делегата — компилятор не может
    // гарантировать, что между строкой is FeedArticleState.Error -> и следующей строкой stateCompose.errorMessage
    // значение не изменится на другое. Поэтому без сохранения в отдельную val — smart cast не срабатывает, и компилятор требует ручной as.
    // Когда ты пишешь when (val state = stateCompose) — state это уже обычная локальная неизменяемая val,
    // компилятор гарантированно знает, что она не поменяется, и smart cast работает нормально.
    when (val feedArticleState = stateCompose) {
        // Loading - это единственный экземпляр, готовый объект. Поэтому в when можно сравнивать напрямую через == (что и происходит без is)
        FeedArticleState.Loading -> FullScreenLoading()

        // Это не значение, а класс (шаблон для создания). "Loaded" не может использоваться как значение — нужны аргументы конструктора, а у тебя
        // нет конкретного article, чтобы это сделать. is здесь означает: "проверь, что stateCompose является экземпляром класса Loaded, независимо
        // от того, какие внутри данные (article)".
        is FeedArticleState.Error -> FullScreenError(
            errorMessage = feedArticleState.errorMessage,
            onRetry = {})

        is FeedArticleState.Loaded -> ArticleDetailsScreen(article = feedArticleState.article)
    }
}

@Composable
private fun ArticleDetailsScreen(modifier: Modifier = Modifier, article: FeedArticle) {
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
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
                        // занимает весь размер Box
                        .matchParentSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    0.0f to Color.Transparent,       // 0% высоты — прозрачно
                                    0.5f to Color.Transparent,       // 50% высоты — всё ещё прозрачно
                                    1.0f to Color.Black.copy(alpha = 0.8f) // 100% (самый низ) — тёмный
                                )
                            )
                        )
                )
                IconButton(
                    onClick = {},
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
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.White
                    )
                }
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
        }
    }
}