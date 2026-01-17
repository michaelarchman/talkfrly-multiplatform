package com.talkfrly.multiplatform.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalTalkfrlyDarkColors = staticCompositionLocalOf<TalkfrlyColors> {
    error("No TalkfrlyColors provided")
}

@Composable
fun TalkfrlyTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) talkfrlyDarkThemeColors() else talkfrlyLightThemeColors()

    CompositionLocalProvider(
        LocalTalkfrlyDarkColors provides colors,
    ) {
        content()
    }
}