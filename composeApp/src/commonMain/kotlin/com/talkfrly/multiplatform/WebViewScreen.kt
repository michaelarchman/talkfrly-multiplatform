package com.talkfrly.multiplatform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState

@Composable
fun WebViewScreen(url: String) {
    val webViewState = rememberWebViewState(url = url)

    LaunchedEffect(webViewState.isLoading) {
        println("WebView loading: ${webViewState.isLoading}")
    }

    LaunchedEffect(webViewState.pageTitle) {
        println("Page title: ${webViewState.pageTitle}")
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

    Box(modifier = Modifier.fillMaxSize()) {
        WebView(
            state = webViewState,
            modifier = Modifier.fillMaxSize()
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