package com.example.spaceexplorer.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SpaceColors.Dark.Primary,
    onPrimary = SpaceColors.Dark.OnPrimary,
    secondary = SpaceColors.Dark.Secondary,
    onSecondary = SpaceColors.Dark.OnSecondary,
    tertiary = SpaceColors.Dark.Tertiary,
    onTertiary = SpaceColors.Dark.OnTertiary,
    error = SpaceColors.Dark.Error,
    onError = SpaceColors.Dark.OnError,
    background = SpaceColors.Dark.Background,
    onBackground = SpaceColors.Dark.OnBackground,
    surface = SpaceColors.Dark.Surface,
    onSurface = SpaceColors.Dark.OnSurface,
    surfaceVariant = SpaceColors.Dark.SurfaceVariant,
    onSurfaceVariant = SpaceColors.Dark.OnSurfaceVariant,
    outline = SpaceColors.Dark.Outline

)

private val LightColorScheme = lightColorScheme(
    primary = SpaceColors.Light.Primary,
    onPrimary = SpaceColors.Light.OnPrimary,
    secondary = SpaceColors.Light.Secondary,
    onSecondary = SpaceColors.Light.OnSecondary,
    tertiary = SpaceColors.Light.Tertiary,
    onTertiary = SpaceColors.Light.OnTertiary,
    error = SpaceColors.Light.Error,
    onError = SpaceColors.Light.OnError,
    background = SpaceColors.Light.Background,
    onBackground = SpaceColors.Light.OnBackground,
    surface = SpaceColors.Light.Surface,
    onSurface = SpaceColors.Light.OnSurface,
    surfaceVariant = SpaceColors.Light.SurfaceVariant,
    onSurfaceVariant = SpaceColors.Light.OnSurfaceVariant,
    outline = SpaceColors.Light.Outline
)

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SpaceExplorerTheme(
    themeMode: ThemeMode,
    content: @Composable () -> Unit
) {

    val colorScheme = when (themeMode) {
        ThemeMode.DARK -> DarkColorScheme
        ThemeMode.LIGHT -> LightColorScheme
        ThemeMode.SYSTEM -> {
            val isSystemDark = isSystemInDarkTheme()

            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    val context = LocalContext.current
                    if (isSystemDark) dynamicDarkColorScheme(context)
                    else dynamicLightColorScheme(context)
                }

                isSystemDark -> DarkColorScheme
                else -> LightColorScheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

enum class ThemeMode {
    DARK,
    LIGHT,
    SYSTEM;

    companion object {

        fun fromName(name: String?): ThemeMode {
            return entries.find { it.name == name } ?: SYSTEM
        }
    }
}