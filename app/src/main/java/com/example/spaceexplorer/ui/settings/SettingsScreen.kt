package com.example.spaceexplorer.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spaceexplorer.R
import com.example.spaceexplorer.ui.theme.ThemeMode

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeMode: ThemeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val isOfflineCachingEnabled: Boolean by viewModel.offlineCachingEnabled.collectAsStateWithLifecycle()

    Scaffold() { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Theme",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            ThemeSelector(
                selected = themeMode,
                onSelect = { viewModel.onThemeChanged(it) }
            )

            Text(
                text = stringResource(R.string.settings_content),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            CachingSwitcher(
                isOfflineCachingEnabled = isOfflineCachingEnabled,
                onToggleChange = { enabled -> viewModel.toggleOfflineCaching(enabled) })
        }
    }
}

@Composable
fun CachingSwitcher(
    isOfflineCachingEnabled: Boolean,
    onToggleChange: (Boolean) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(start = 16.dp),
                imageVector = Icons.Default.Bookmark,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .weight(1F),
                text = stringResource(R.string.settings_offline_caching),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Switch(
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp),
                checked = isOfflineCachingEnabled,
                onCheckedChange = { enabled ->
                    onToggleChange(enabled)
                }
            )
        }
    }
}

@Composable
fun ThemeSelector(
    selected: ThemeMode,
    onSelect: (ThemeMode) -> Unit
) {
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SegmentedButton(
            selected = selected == ThemeMode.LIGHT,
            onClick = { onSelect(ThemeMode.LIGHT) },
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3),
            icon = {
                Icon(Icons.Default.LightMode, contentDescription = null)
            }
        ) {
            Text(stringResource(R.string.light_mode))
        }
        SegmentedButton(
            selected = selected == ThemeMode.DARK,
            onClick = { onSelect(ThemeMode.DARK) },
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3),
            icon = {
                Icon(Icons.Default.DarkMode, contentDescription = null)
            }
        ) {
            Text(stringResource(R.string.dark_mode))
        }
        SegmentedButton(
            selected = selected == ThemeMode.SYSTEM,
            onClick = { onSelect(ThemeMode.SYSTEM) },
            shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3),
            icon = {
                Icon(Icons.Default.SettingsBrightness, contentDescription = null)
            }
        ) {
            Text(stringResource(R.string.system_mode))
        }
    }
}