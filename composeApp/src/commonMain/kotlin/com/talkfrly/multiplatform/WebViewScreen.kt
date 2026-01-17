package com.talkfrly.multiplatform

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import com.multiplatform.webview.web.rememberWebViewNavigator

@Composable
expect fun createPlatformParams(launcher: (Any, (Any?) -> Unit) -> Unit): Any?

@Composable
fun WebViewScreen(url: String) {
    val webViewState = rememberWebViewState(url = url)
    val isDarkTheme = isSystemInDarkTheme()
    val navigator = rememberWebViewNavigator()

    LaunchedEffect(webViewState.isLoading, isDarkTheme) {
        if (!webViewState.isLoading) {
            val themeValue = if (isDarkTheme) "dark" else "light"
            val script = """
                window.appTheme = '$themeValue';
                window.dispatchEvent(new Event('appThemeChange'));
            """.trimIndent()
            navigator.evaluateJavaScript(script)
        }
    }

    DisposableEffect(Unit) {
        webViewState.webSettings.apply {
            isJavaScriptEnabled = true
            allowUniversalAccessFromFileURLs = true
            androidWebSettings.apply {
                domStorageEnabled = true
            }
        }
        onDispose { }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        @Suppress("UNCHECKED_CAST")
        WebView(
            state = webViewState,
            navigator = navigator,
            modifier = Modifier.fillMaxSize(),
        )

        if (webViewState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
