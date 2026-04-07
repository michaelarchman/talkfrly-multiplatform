package com.talkfrly.multiplatform.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreenRoot(
    navController: NavController,
    viewModel: ProfileViewModel = koinViewModel<ProfileViewModel>(),
) {
    val state by viewModel.state.collectAsState() //read only

    ProfileScreen(
        state = state,
        navController = navController,
        onAction = { viewModel.onIntent(it) }
    )
}

@Composable
private fun ProfileScreen(
    state: ProfileState,
    navController: NavController,
    onAction: (ProfileIntent) -> Unit,
) {
    Scaffold{ paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Text(state.userId)
            Text(state.userName)
            state.error?.let {
                Text(
                    text = it,
                    color = Color.Red
                )
            }
            Button(onClick = { onAction(ProfileIntent.GetUsername) }) {
                Text("Get user Name - intent")
            }
        }
    }
}