package com.talkfrly.multiplatform.ui.session

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.talkfrly.multiplatform.ui.Route

@Composable
fun Session(
    sessionViewModel: SessionViewModel,
    navController: NavHostController,
    content: @Composable () -> Unit,
) {

    val sessionState by sessionViewModel.state.collectAsState()

    LaunchedEffect(sessionState) {
        when (sessionState) {
            SessionState.LoggedIn ->
                navController.navigate(Route.Home.id) { popUpTo(0) }
            SessionState.LoggedOut ->
                navController.navigate(Route.Login.id) { popUpTo(0) }
            SessionState.Loading ->
                navController.navigate(Route.Splash.id) { popUpTo(0) }
            SessionState.Error ->
                navController.navigate(Route.Error.id) { popUpTo(0) }
        }
    }

    content()
}
