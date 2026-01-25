package com.talkfrly.multiplatform.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.ui.Route
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left
import talkfrly_multiplatform.composeapp.generated.resources.person

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeState,
    navController: NavController,
    onAction: (HomeIntent) -> Unit,
    onLogout: () -> Unit,
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Talkfrly") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalTalkfrlyColors.current.background,
                    titleContentColor = LocalTalkfrlyColors.current.body,
                    navigationIconContentColor = LocalTalkfrlyColors.current.body,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {},
                        enabled = true,
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.chevron_left),
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    if (navController.currentBackStackEntry?.destination?.route == Route.Home.id) {
                        IconButton(
                            onClick = { navController.navigate(Route.Account.id) },
                            enabled = true,
                        ) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.person),
                                contentDescription = null,
                            )
                        }
                    }
                }
            )
        },
        containerColor = LocalTalkfrlyColors.current.background,
        contentColor = LocalTalkfrlyColors.current.body,
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            state.publications?.let { publicationList ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(publicationList.publications) { publication ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = LocalTalkfrlyColors.current.background,
                                contentColor = LocalTalkfrlyColors.current.body,
                            ),
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                            ) {
                                Text(text = "By: ${publication.user?.displayName ?: "Anonymous"}")
                                Text(text = publication.content)
                                Text(text = "Views: ${publication.views ?: 0} | Likes: ${publication.voteScore}")
                            }
                        }
                    }
                }
            }
        }
    }
}