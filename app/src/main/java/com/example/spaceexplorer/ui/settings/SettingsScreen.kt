package com.example.spaceexplorer.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spaceexplorer.R


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Scaffold() { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.settings_appearance),
                style = MaterialTheme.typography.titleLarge
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "\uD83C\uDF4C",
                        fontSize = 48.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(1F),
                        text = stringResource(R.string.settings_dark_mode),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Switch(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 16.dp),
                        checked = true,
                        onCheckedChange = {}
                    )
                }
            }

            Text(
                text = stringResource(R.string.settings_content),
                style = MaterialTheme.typography.titleLarge
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "\uD83D\uDCBE",
                        fontSize = 48.sp
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .weight(1F),
                        text = stringResource(R.string.settings_offline_caching),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Switch(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 16.dp),
                        checked = true,
                        onCheckedChange = {}
                    )
                }
            }
        }
    }
}