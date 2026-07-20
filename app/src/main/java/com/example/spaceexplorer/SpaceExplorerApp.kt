package com.example.spaceexplorer

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.spaceexplorer.ui.favorites.FavoritesScreen
import com.example.spaceexplorer.ui.feed.detail.FeedArticleDetailScreen
import com.example.spaceexplorer.ui.feed.list.FeedScreen
import com.example.spaceexplorer.ui.launches.LaunchesScreen
import com.example.spaceexplorer.ui.settings.SettingsScreen

@PreviewScreenSizes
@Composable
fun SpaceExplorerApp() {
    val navController: NavHostController = rememberNavController()
    val backStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentRoute: String? = backStackEntry?.destination?.route

    val isTopLevelDestination: Boolean = AppDestinations.entries.any { it.route == currentRoute }

    if (isTopLevelDestination) {
        NavigationSuiteScaffold(
            // Draw navBar items and resolve clicks on them
            navigationSuiteItems = {
                AppDestinations.entries.forEach { destination ->
                    item(
                        icon = { Icon(destination.icon, contentDescription = destination.label) },
                        label = { Text(destination.label) },
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                // Pop up to Home when Back button is pressed
                                popUpTo(navController.graph.findStartDestination().id) {
                                    // Save state of the popped screens
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination on the top of a backstack
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            },
            // Draw on the remaining area of the screen
            content = {
                SpaceExplorerNavHost(navController)
            }
        )
    } else {
        SpaceExplorerNavHost(navController)
    }
}

@Composable
private fun SpaceExplorerNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = AppDestinations.HOME.route,
        builder = {
            // Home section
            composable(route = AppDestinations.HOME.route) {
                FeedScreen(
                    viewModel = hiltViewModel()
                )
            }

            composable(route = FeedArticleDetailRoute.ROUTE) {
                FeedArticleDetailScreen()
            }

            // Launches section
            composable(route = AppDestinations.LAUNCHES.route) {
                LaunchesScreen(viewModel = hiltViewModel())
            }

            // Favorites section
            composable(route = AppDestinations.FAVORITES.route) {
                FavoritesScreen()
            }

            // Settings section
            composable(route = AppDestinations.SETTINGS.route) {
                SettingsScreen()
            }
        }
    )
}
