package com.talkfrly.multiplatform

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.talkfrly.multiplatform.ui.TalkfrlyTheme

const val TALKFRLY_URL = "https://talkfrly.com/"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val isDarkTheme = isSystemInDarkTheme()

    TalkfrlyTheme(darkTheme = isDarkTheme) {
        Scaffold (
            topBar = {
                TopAppBar(
                    title = { Text("Talkfrly") },
                    modifier = Modifier
                        .height(40.dp)
                        .background(MaterialTheme.colorScheme.background),
                )
            },
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets()
        ) { paddingValues ->
            Column (modifier = Modifier.padding(paddingValues)) {
                WebViewScreen(TALKFRLY_URL)
            }
        }
    }
}