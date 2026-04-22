package com.talkfrly.multiplatform.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.talkfrly.multiplatform.ui.screens.account.AccountScreenRoot
import com.talkfrly.multiplatform.ui.screens.createpublication.CreatePublicationScreenRoot
import com.talkfrly.multiplatform.ui.screens.error.ErrorScreenRoot
import com.talkfrly.multiplatform.ui.screens.home.HomeScreenRoot
import com.talkfrly.multiplatform.ui.screens.login.LoginScreenRoot
import com.talkfrly.multiplatform.ui.screens.publication.PublicationScreenRoot
import com.talkfrly.multiplatform.ui.screens.register.RegisterScreenRoot
import com.talkfrly.multiplatform.ui.screens.splash.SplashScreen
import com.talkfrly.multiplatform.ui.screens.stream.StreamScreenRoot
import com.talkfrly.multiplatform.ui.screens.verifyemail.VerifyEmailScreenRoot
import com.talkfrly.multiplatform.ui.screens.verifyemail.VerifyEmailViewModel
import com.talkfrly.multiplatform.ui.session.SessionViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    sessionViewModel: SessionViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
    ) {
        composable<LoginRoute> {
            LoginScreenRoot(
                 navController = navController,
            )
        }
        composable<RegisterRoute>{
            RegisterScreenRoot(
                viewModel = koinViewModel(),
                navController = navController,
            )
        }
        composable<VerifyEmailRoute> {
            VerifyEmailScreenRoot(
                viewModel = koinViewModel<VerifyEmailViewModel>(),
                navController = navController,
            )
        }
        composable<SplashRoute> {
            SplashScreen()
        }
        composable<HomeRoute> {
            HomeScreenRoot(
                viewModel = koinViewModel(),
                navController = navController,
            )
        }
        composable<AccountRoute>{
            AccountScreenRoot(
                viewModel = koinViewModel(),
                navController = navController,
                onLogout = { sessionViewModel.logout() }
            )
        }
        composable<PublicationDetailsRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<PublicationDetailsRoute>()
            PublicationScreenRoot(
                publicationId = route.publicationId,
                viewModel = koinViewModel(),
                navController = navController,
            )
        }
        composable<NewPublicationRoute> {
            CreatePublicationScreenRoot(
                navController = navController,
            )
        }
        composable<ErrorRoute>{
           ErrorScreenRoot(navController = navController)
        }
        composable<StreamRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<StreamRoute>()
            StreamScreenRoot(
                streamId = route.streamId,
                navController = navController,
            )
        }
    }
}
