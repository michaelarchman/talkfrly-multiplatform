package com.talkfrly.multiplatform.ui.screens.publication

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.talkfrly.multiplatform.ui.components.publications.PublicationCard
import com.talkfrly.multiplatform.ui.components.publications.PublicationViewMode
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left

@Composable
fun PublicationDetailsScreenRoot(
    viewModel: PublicationDetailsViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(PublicationDetailsIntent.GetPublicationDetails)
    }

    PublicationDetailsScreen(
        state = state,
        navController = navController,
        onAction = { intent ->
            when (intent) {
                PublicationDetailsIntent.NavigateBack -> navController.popBackStack()
                else -> viewModel.onIntent(intent)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PublicationDetailsScreen(
    state: PublicationDetailsState,
    navController: NavController,
    onAction: (PublicationDetailsIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Publication")
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(PublicationDetailsIntent.NavigateBack) }) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.chevron_left),
                            contentDescription = "Back",
                            tint = LocalTalkfrlyColors.current.body,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalTalkfrlyColors.current.background,
                    titleContentColor = LocalTalkfrlyColors.current.body,
                    navigationIconContentColor = LocalTalkfrlyColors.current.body,
                ),
            )
        }
    ) { paddingValues ->
        when {
            state.publication != null -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
                        PublicationCard(
                            publication = state.publication,
                            viewMode = PublicationViewMode.DETAILS,
                            onClick = null,
                        )
                    }
                }
            }
            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage,
                    color = LocalTalkfrlyColors.current.body,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                )
            }
        }
    }
}