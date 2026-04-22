package com.talkfrly.multiplatform

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.talkfrly.multiplatform.ui.nav.AppNavHost
import com.talkfrly.multiplatform.ui.nav.HomeRoute
import com.talkfrly.multiplatform.ui.nav.LoginRoute
import com.talkfrly.multiplatform.ui.nav.SplashRoute
import com.talkfrly.multiplatform.ui.session.SessionState
import com.talkfrly.multiplatform.ui.session.SessionViewModel
import com.talkfrly.multiplatform.ui.theme.TalkfrlyTheme
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    viewModel: AppViewModel = koinViewModel<AppViewModel>()
) {
    val sessionViewModel: SessionViewModel = koinViewModel<SessionViewModel>()
    val sessionState by sessionViewModel.state.collectAsState()
    val navController = rememberNavController()

    LaunchedEffect(sessionState) {
        try {
            if (navController.graph.nodes.isEmpty()) return@LaunchedEffect
        } catch (e: IllegalStateException) {
            println("Session state didn't initialized: $e")
            return@LaunchedEffect
        }

        when (sessionState) {
            SessionState.LoggedIn -> {
                navController.navigate(HomeRoute) {
                    popUpTo(0)
                }
            }
            SessionState.LoggedOut -> {
                navController.navigate(LoginRoute) {
                    popUpTo(0)
                }
            }
            SessionState.Loading -> {
                navController.navigate(SplashRoute) {
                    popUpTo(0)
                }
            }
        }
    }

    TalkfrlyTheme {
        AppNavHost(
            navController = navController,
            sessionViewModel = sessionViewModel
        )


//        if (isGlobalLoading) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(LocalTalkfrlyColors.current.background.copy(alpha = 0.9f)),
//                contentAlignment = Alignment.Center
//            ) {
//                Column(
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(
//                        bitmap = if (isSystemInDarkTheme())
//                            imageResource(Res.drawable.talkfrly_logo_dark)
//                        else imageResource(Res.drawable.talkfrly_logo_light),
//                        contentDescription = null,
//                        modifier = Modifier.size(140.dp)
//                    )
//                    CircularProgressIndicator(
//                        color = LocalTalkfrlyColors.current.primary,
//                        trackColor = LocalTalkfrlyColors.current.surface
//                    )
//                }
//            }
//        }
    }
}