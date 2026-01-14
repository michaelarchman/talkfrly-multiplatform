package com.talkfrly.multiplatform.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkBackground,
    background = DarkBackground,
    onBackground = DarkText,
    surface = DarkBackground,
    onSurface = DarkText,
    secondary = DarkPrimary.copy(alpha = 0.7f),
    onSecondary = DarkBackground,
    tertiary = DarkPrimary.copy(alpha = 0.5f),
    onTertiary = DarkBackground
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightBackground,
    background = LightBackground,
    onBackground = LightText,
    surface = LightBackground,
    onSurface = LightText,
    secondary = LightPrimary.copy(alpha = 0.7f),
    onSecondary = LightBackground,
    tertiary = LightPrimary.copy(alpha = 0.5f),
    onTertiary = LightBackground
)

@Composable
fun TalkfrlyTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}