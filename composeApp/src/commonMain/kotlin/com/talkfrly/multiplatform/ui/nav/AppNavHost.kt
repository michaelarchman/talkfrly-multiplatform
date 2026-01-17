package com.talkfrly.multiplatform.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.talkfrly.multiplatform.ui.session.SessionViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.talkfrly.multiplatform.ui.Route
import com.talkfrly.multiplatform.ui.screens.home.HomeScreenRoot
import com.talkfrly.multiplatform.ui.screens.login.LoginScreenRoot
import com.talkfrly.multiplatform.ui.screens.register.RegisterScreenRoot
import com.talkfrly.multiplatform.ui.screens.verifyemail.VerifyEmailScreenRoot
import com.talkfrly.multiplatform.ui.screens.verifyemail.VerifyEmailViewModel
import com.talkfrly.multiplatform.ui.session.SessionState

@Composable
fun AppNavHost(
    sessionViewModel: SessionViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val state by sessionViewModel.state.collectAsState()

    when (state) {
        SessionState.Loading -> {}
        SessionState.LoggedOut -> {
            NavHost(
                startDestination = Route.Login.id,
                navController = navController
            ) {
                composable(Route.Login.id) {
                    LoginScreenRoot(
                        viewModel = koinViewModel(),
                        navController = navController,
                        onLoginSuccess = { sessionViewModel.checkSession() },
                    )
                }
                composable(Route.Register.id) {
                    RegisterScreenRoot(
                        viewModel = koinViewModel(),
                        navController = navController,
                    )
                }
                composable(Route.VerifyEmail.id) {
                    VerifyEmailScreenRoot(
                        viewModel = koinViewModel<VerifyEmailViewModel>(),
                        navController = navController,
                        onVerifySuccess = { sessionViewModel.checkSession() } as (() -> Unit)?,
                    )
                }
            }
        }
        SessionState.LoggedIn-> {
            NavHost(
                startDestination = Route.Home.id,
                navController = navController
            ) {
                composable(Route.Home.id) {
                    HomeScreenRoot(
                        viewModel = koinViewModel(),
                        navController = navController,
                        onLogout = { sessionViewModel.logout() }
                    )
                }
            }
        }
    }

    // Reset navigation when session state changes
    LaunchedEffect(state) {
        if (state == SessionState.LoggedOut) {
            navController.navigate(Route.Login.id) {
                popUpTo(Route.Login.id) { inclusive = true }
            }
        }
    }
}