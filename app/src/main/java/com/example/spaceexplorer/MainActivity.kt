package com.example.spaceexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.spaceexplorer.ui.favorites.FavoritesScreen
import com.example.spaceexplorer.ui.feed.FeedScreen
import com.example.spaceexplorer.ui.launches.LaunchesScreen
import com.example.spaceexplorer.ui.settings.SettingsScreen
import com.example.spaceexplorer.ui.theme.SpaceExplorerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpaceExplorerTheme {
                SpaceExplorerApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun SpaceExplorerApp() {
    var currentDestination: AppDestinations by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {

        when (currentDestination) {
            AppDestinations.HOME -> FeedScreen()
            AppDestinations.LAUNCHES -> LaunchesScreen()
            AppDestinations.FAVORITES -> FavoritesScreen()
            AppDestinations.SETTINGS -> SettingsScreen()
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Feed", Icons.Default.Home),
    LAUNCHES("Launches", Icons.Default.RocketLaunch),
    FAVORITES("Favorites", Icons.Default.Favorite),
    SETTINGS("Settings", Icons.Default.Settings),
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpaceExplorerTheme {
        Greeting("Android")
    }
}