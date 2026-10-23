package com.tianmen.ngz.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val NgzColorScheme = darkColorScheme(
    primary = Jade,
    onPrimary = InkBlack,
    primaryContainer = JadeContainer,
    onPrimaryContainer = Jade,
    secondary = JadeDim,
    onSecondary = InkBlack,
    tertiary = AmberAlert,
    onTertiary = InkBlack,
    background = InkBlack,
    onBackground = TextPrimary,
    surface = InkSurface,
    onSurface = TextPrimary,
    surfaceVariant = InkSurfaceHigh,
    onSurfaceVariant = TextSecondary,
    outline = InkOutline,
    outlineVariant = InkOutline,
    error = Crimson,
    onError = InkBlack,
)

@Composable
fun NgzTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = NgzColorScheme,
        typography = NgzTypography,
        content = content,
    )
}
