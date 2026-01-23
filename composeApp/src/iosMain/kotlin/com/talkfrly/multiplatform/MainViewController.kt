package com.talkfrly.multiplatform

import androidx.compose.ui.window.ComposeUIViewController
import com.talkfrly.multiplatform.di.initKoin
import com.talkfrly.multiplatform.di.iosModule

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin(iosModule) }
) {
    App()
}