package com.talkfrly.multiplatform

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import com.talkfrly.multiplatform.ui.theme.TalkfrlyTheme

const val TALKFRLY_URL = "https://talkfrly.com"

val LocalFileChooserLauncher = compositionLocalOf<((Any, (Any?) -> Unit) -> Unit)?> { null }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(fileChooserLauncher: ((Any, (Any?) -> Unit) -> Unit)? = null) {
    val isDarkTheme = isSystemInDarkTheme()

    CompositionLocalProvider(
        LocalFileChooserLauncher provides fileChooserLauncher
    ) {
        TalkfrlyTheme(darkTheme = isDarkTheme) {
            Scaffold (
                topBar = {
                    TopAppBar(
                        title = { Text("Talkfrly") }
                    )
                },
                modifier = Modifier.fillMaxSize(),
                contentWindowInsets = WindowInsets.systemBars
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                )
            }
        }
    }
}