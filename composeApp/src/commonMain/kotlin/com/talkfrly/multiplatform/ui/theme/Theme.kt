package com.talkfrly.multiplatform.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

val LocalTalkfrlyColors = staticCompositionLocalOf<TalkfrlyColors> {
    error("No TalkfrlyColors provided")
}

@Composable
fun TalkfrlyTheme(
    content: @Composable () -> Unit,
) {
    val colors = if (isSystemInDarkTheme()) talkfrlyDarkThemeColors() else talkfrlyLightThemeColors()

    CompositionLocalProvider(
        LocalTalkfrlyColors provides colors,
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(LocalTalkfrlyColors.current.background)
        ) {
            content()
        }
    }
}