package com.talkfrly.multiplatform.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavController,
    onLogout: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    HomeScreen(
        state = state,
        navController = navController,
        onAction = { intent ->
            viewModel.onIntent(intent)
        },
        onLogout = onLogout
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    navController: NavController,
    onAction: (HomeIntent) -> Unit,
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = state.message)
        Button(
            onClick = { onLogout() }

        ) {
            Text("Logout")
        }
    }
}