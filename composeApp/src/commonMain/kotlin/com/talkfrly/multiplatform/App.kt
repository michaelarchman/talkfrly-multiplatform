package com.talkfrly.multiplatform

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.talkfrly.multiplatform.ui.nav.AppNavHost
import com.talkfrly.multiplatform.ui.session.Session
import com.talkfrly.multiplatform.ui.theme.TalkfrlyTheme
import org.koin.compose.viewmodel.koinViewModel

val LocalFileChooserLauncher = compositionLocalOf<((Any, (Any?) -> Unit) -> Unit)?> { null }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    fileChooserLauncher: ((Any, (Any?) -> Unit) -> Unit)? = null,
    viewModel: AppViewModel = koinViewModel<AppViewModel>()
) {
    val isDarkTheme = isSystemInDarkTheme()
    val state by viewModel.state.collectAsState()

    CompositionLocalProvider(
        LocalFileChooserLauncher provides fileChooserLauncher
    ) {
        Session()
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
                AppNavHost()
            }
        }
    }
}