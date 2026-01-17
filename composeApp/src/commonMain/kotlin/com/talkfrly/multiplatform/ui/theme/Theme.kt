package com.talkfrly.multiplatform.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalTalkfrlyDarkColors = staticCompositionLocalOf<TalkfrlyColors> {
    error("No TalkfrlyColors provided")
}

@Composable
fun TalkfrlyTheme(
    content: @Composable () -> Unit
) {
    val colors = if (isSystemInDarkTheme()) talkfrlyDarkThemeColors() else talkfrlyLightThemeColors()

    CompositionLocalProvider(
        LocalTalkfrlyDarkColors provides colors,
    ) {
        content()
    }
}