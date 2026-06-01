package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AccentCyan,
    secondary = AccentBlue,
    tertiary = ActiveGreen,
    background = DarkBg,
    surface = DarkSurface,
    onBackground = TextWhite,
    onSurface = TextWhite,
    surfaceVariant = DarkCard
)

private val LightColorScheme = lightColorScheme(
    primary = AccentBlue,
    secondary = AccentCyan,
    tertiary = ActiveGreen,
    background = LightBg,
    surface = LightSurface,
    onBackground = LightText,
    onSurface = LightText,
    surfaceVariant = LightCard
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // We force custom premium theme colors to match Nike/Apple aesthetic exactly!
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
