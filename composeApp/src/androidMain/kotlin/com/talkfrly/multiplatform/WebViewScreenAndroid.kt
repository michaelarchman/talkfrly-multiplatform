package com.talkfrly.multiplatform

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.multiplatform.webview.web.PlatformWebViewParams

@Composable
actual fun createPlatformParams(launcher: (Any, (Any?) -> Unit) -> Unit): Any? {
    val context = LocalContext.current
    return PlatformWebViewParams(
        chromeClient = FileChooserWebChromeClient(
            context = context,
            fileChooserLauncher = launcher
        )
    )
}