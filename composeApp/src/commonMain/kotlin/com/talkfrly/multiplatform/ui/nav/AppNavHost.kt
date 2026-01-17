package com.talkfrly.multiplatform.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.talkfrly.multiplatform.ui.session.SessionViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.talkfrly.multiplatform.ui.Route
import com.talkfrly.multiplatform.ui.screens.login.LoginScreenRoot

@Composable
fun AppNavHost(
    sessionViewModel: SessionViewModel = koinViewModel<SessionViewModel>(),
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        startDestination = Route.Login.id,
        navController = navController
    ) {
        composable(Route.Login.id) {
            LoginScreenRoot(
                viewModel = koinViewModel(),
                onLoginSuccess = { sessionViewModel.checkSession() },
                modifier = modifier,
            )
        }
    }
}