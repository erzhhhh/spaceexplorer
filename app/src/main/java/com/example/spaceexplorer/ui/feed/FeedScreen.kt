package com.example.spaceexplorer.ui.feed

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spaceexplorer.Greeting

@Composable
fun FeedScreen(
    viewModel: FeedViewModel
) {

    val state: FeedState by viewModel.feed.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Greeting(
            name = "Home screen",
            modifier = Modifier.padding(innerPadding)
        )
    }
}