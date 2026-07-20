package com.example.spaceexplorer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

// TODO use type-safe route
enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
    val route: String
) {
    HOME("Feed", Icons.Default.Home, "home"),
    LAUNCHES("Launches", Icons.Default.RocketLaunch, "launches"),
    FAVORITES("Favorites", Icons.Default.Favorite, "favorites"),
    SETTINGS("Settings", Icons.Default.Settings, "settings")
}

// Separate from enum, because it's not a top-level destination
object FeedArticleDetailRoute {
    const val ROUTE = "article/articleId"

    fun createRoute(articleId: String) = "article/$articleId"
}