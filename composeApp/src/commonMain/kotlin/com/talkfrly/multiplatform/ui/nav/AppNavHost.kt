package com.talkfrly.multiplatform.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.talkfrly.multiplatform.ui.Route
import com.talkfrly.multiplatform.ui.screens.account.AccountScreenRoot
import com.talkfrly.multiplatform.ui.screens.createpublication.CreatePublicationScreenRoot
import com.talkfrly.multiplatform.ui.screens.home.HomeScreenRoot
import com.talkfrly.multiplatform.ui.screens.login.LoginScreenRoot
import com.talkfrly.multiplatform.ui.screens.publication.PublicationDetailsScreenRoot
import com.talkfrly.multiplatform.ui.screens.register.RegisterScreenRoot
import com.talkfrly.multiplatform.ui.screens.splash.SplashScreen
import com.talkfrly.multiplatform.ui.screens.verifyemail.VerifyEmailScreenRoot
import com.talkfrly.multiplatform.ui.screens.verifyemail.VerifyEmailViewModel
import com.talkfrly.multiplatform.ui.session.SessionViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    sessionViewModel: SessionViewModel,
) {
    val state by sessionViewModel.state.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Route.Splash.id
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
                onVerifySuccess = { sessionViewModel.checkSession() },
            )
        }
        composable(Route.Splash.id) {
            SplashScreen()
        }
        composable(Route.Home.id) {
            HomeScreenRoot(
                viewModel = koinViewModel(),
                navController = navController,
            )
        }
        composable(Route.Account.id) {
            AccountScreenRoot(
                viewModel = koinViewModel(),
                navController = navController,
                onLogout = { sessionViewModel.logout() }
            )
        }
        composable<Route.PublicationDetails> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.PublicationDetails>()
            PublicationDetailsScreenRoot(
                publicationId = route.publicationId,
                viewModel = koinViewModel(),
                navController = navController,
            )
        }
        composable<Route.CreatePublication> { backStackEntry ->
            val route = backStackEntry.toRoute<Route.CreatePublication>()
            CreatePublicationScreenRoot(
                navController = navController,
                threadId = route.threadId,
                threadName = route.threadName,
                viewModel = koinViewModel()
            )
        }
    }
}
