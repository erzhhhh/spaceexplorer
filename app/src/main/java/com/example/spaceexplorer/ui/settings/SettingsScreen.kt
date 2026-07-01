package com.example.spaceexplorer.ui.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.spaceexplorer.Greeting

@Composable
fun SettingsScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Greeting(
            name = "Settings screen",
            modifier = Modifier.padding(innerPadding)
        )
    }
}