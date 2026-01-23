package com.talkfrly.multiplatform.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavController,
    onLogout: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(HomeIntent.GetPublications)
    }

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
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = onLogout) {
            Text("Logout")
        }

        state.publications?.let { publicationList ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(publicationList.publications) { publication ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "By: ${publication.user?.displayName ?: publication.pseudonym ?: "Anonymous"}")
                            Text(text = publication.content)
                            Text(text = "Views: ${publication.views ?: 0} | Likes: ${publication.voteScore}")
                        }
                    }
                }
            }
        }
    }
}