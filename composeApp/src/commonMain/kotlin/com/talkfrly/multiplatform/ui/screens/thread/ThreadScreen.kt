package com.talkfrly.multiplatform.ui.screens.thread

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left

@Composable
fun ThreadScreenRoot(
    viewModel: ThreadViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(ThreadIntent.GetThreads)
    }

    ThreadScreen(
        state = state,
        onBackClick = { navController.popBackStack() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThreadScreen(
    state: ThreadState,
    onBackClick: () -> Unit,
) {
    Scaffold(
        containerColor = LocalTalkfrlyColors.current.background,
        contentColor = LocalTalkfrlyColors.current.body,
        topBar = {
            TopAppBar(
                title = { Text("Thread") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalTalkfrlyColors.current.background,
                    titleContentColor = LocalTalkfrlyColors.current.body,
                    navigationIconContentColor = LocalTalkfrlyColors.current.body,
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.chevron_left),
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text(
                    text = "Threads",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            item {
                Text("Total count: ${state.totalCount}")
            }

            item {
                Text("Page: ${state.page}, limit: ${state.limit}")
            }

            if (state.isLoading) {
                item {
                    Text("Loading threads...")
                }
            }

            state.errorMessage?.let { message ->
                item {
                    Text(
                        text = message,
                        color = Color.Red,
                    )
                }
            }

            items(state.threads, key = { it.id }) { thread ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = LocalTalkfrlyColors.current.primary60,
                                shape = RoundedCornerShape(12.dp),
                            )
                            .background(
                                color = LocalTalkfrlyColors.current.backgroundDarker,
                                shape = RoundedCornerShape(12.dp),
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = thread.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text("ID: ${thread.id}")
                    Text("Slug: ${thread.slug}")
                    Text("Description: ${thread.description ?: "-"}")
                    Text("Creator ID: ${thread.creatorId}")
                    Text("Members: ${thread.memberCount}")
                    Text("Is member: ${thread.isMember}")
                    Text("Role: ${thread.role ?: "-"}")
                    Text("Created: ${thread.createdAt}")
                    Text("Updated: ${thread.updatedAt}")
                }
            }

            if (!state.isLoading && state.threads.isEmpty() && state.errorMessage == null) {
                item {
                    Text("No threads found")
                }
            }
        }
    }
}