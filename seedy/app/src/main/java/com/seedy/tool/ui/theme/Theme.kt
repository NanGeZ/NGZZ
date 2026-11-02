package com.seedy.tool.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF2B5CE6),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDCE4FF),
    onPrimaryContainer = Color(0xFF0B1B4B),
    secondary = Color(0xFF00897B),
    secondaryContainer = Color(0xFFB2F1EA),
    background = Color(0xFFF6F7FB),
    surface = Color.White,
    surfaceVariant = Color(0xFFEDEFF6),
    onSurfaceVariant = Color(0xFF44474F),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFB4C4FF),
    onPrimary = Color(0xFF0B2A78),
    primaryContainer = Color(0xFF22429C),
    onPrimaryContainer = Color(0xFFDCE4FF),
    secondary = Color(0xFF4EDCCC),
    background = Color(0xFF111318),
    surface = Color(0xFF171A21),
    surfaceVariant = Color(0xFF22262E),
    onSurfaceVariant = Color(0xFFC4C6CF),
)

@Composable
fun SeedyTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) DarkColors else LightColors
    MaterialTheme(colorScheme = colors, content = content)
}
