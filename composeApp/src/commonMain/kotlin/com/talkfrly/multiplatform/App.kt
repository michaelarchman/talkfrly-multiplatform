package com.talkfrly.multiplatform


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.talkfrly.multiplatform.ui.Route
import com.talkfrly.multiplatform.ui.nav.AppNavHost
import com.talkfrly.multiplatform.ui.session.Session
import com.talkfrly.multiplatform.ui.session.SessionState
import com.talkfrly.multiplatform.ui.session.SessionViewModel
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import com.talkfrly.multiplatform.ui.theme.TalkfrlyTheme
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    viewModel: AppViewModel = koinViewModel<AppViewModel>()
) {
    val state by viewModel.state.collectAsState()
    val sessionViewModel: SessionViewModel = koinViewModel<SessionViewModel>()
    val sessionState by sessionViewModel.state.collectAsState()
    val navController = rememberNavController()



    TalkfrlyTheme {
        Scaffold (
            topBar = {
                TopAppBar(
                    title = { Text("Talkfrly") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = LocalTalkfrlyColors.current.background,
                        titleContentColor = LocalTalkfrlyColors.current.body,
                    )
                )
            },
            containerColor = LocalTalkfrlyColors.current.background,
            contentColor = LocalTalkfrlyColors.current.body,
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.systemBars
        ) { paddingValues ->
            LaunchedEffect(sessionState) {
                if (sessionState == SessionState.LoggedOut) {
                    println("LaunchedEffect triggered - navigating to Login")
                    navController.navigate(Route.Login.id) {
                        popUpTo(Route.Home.id) { inclusive = true }
                    }
                }
            }
            when (sessionState) {
                SessionState.Loading -> Text("Loading...")
                SessionState.LoggedIn, SessionState.LoggedOut -> {
                    AppNavHost(
                        navController = navController,
                        sessionViewModel = sessionViewModel,
                    )
                }
            }
        }
    }
}