package com.talkfrly.multiplatform.ui.screens.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AccountScreenRoot(
    viewModel: AccountViewModel = koinViewModel(),
    navController: NavController,
    onLogout: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    AccountScreen(
        state = state,
        navController = navController,
        onAction = { intent ->
            viewModel.onIntent(intent, onLogoutSuccess = { onLogout() })
        },
    )
}

@Composable
private fun AccountScreen(
    state: AccountState,
    navController: NavController,
    onAction: (AccountIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = state.message)

        Button(
            onClick = { onAction(AccountIntent.Logout) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Logout")
        }
    }
}