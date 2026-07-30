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
    // We use this constant to prevent typos
    const val ARTICLE_ID_ARG = "articleId"

    const val ROUTE = "article/{$ARTICLE_ID_ARG}"

    // This method is needed for building a route with a variable ArticleId
    // If the route doesn't need a variable, we use a ROUTE constant
    fun createRoute(articleId: Int) = "article/$articleId"
}

object LaunchArticleDetailRoute {

    // We use this constant to prevent typos
    const val ARTICLE_ID_ARG = "articleId"

    const val ROUTE = "report/{$ARTICLE_ID_ARG}"

    // This method is needed for building a route with a variable ArticleId
    // If the route doesn't need a variable, we use a ROUTE constant
    fun createRoute(articleId: Int) = "report/$articleId"
}