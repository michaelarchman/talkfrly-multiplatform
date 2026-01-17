package com.talkfrly.multiplatform


import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.talkfrly.multiplatform.ui.nav.AppNavHost
import com.talkfrly.multiplatform.ui.session.Session
import com.talkfrly.multiplatform.ui.theme.TalkfrlyTheme
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    viewModel: AppViewModel = koinViewModel<AppViewModel>()
) {
    val state by viewModel.state.collectAsState()

    Session()
    TalkfrlyTheme() {
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