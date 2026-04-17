package com.talkfrly.multiplatform.ui.screens.stream

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.talkfrly.multiplatform.ui.components.streams.StreamVideoPlayer
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left
import talkfrly_multiplatform.composeapp.generated.resources.icon_chat

@Composable
fun StreamScreenRoot(
    streamId: String,
    viewModel: StreamViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(streamId) {
        viewModel.initialize(streamId)
        viewModel.onIntent(StreamIntent.LoadStream)
    }

    StreamScreen(
        state = state,
        onAction = { intent ->
            when (intent) {
                StreamIntent.NavigateBack -> navController.popBackStack()
                else -> viewModel.onIntent(intent)
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StreamScreen(
    state: StreamState,
    onAction: (StreamIntent) -> Unit,
) {
    val colors = LocalTalkfrlyColors.current

    Scaffold(
        containerColor = colors.background,
        contentColor = colors.body,
        topBar = {
            TopAppBar(
                title = {
                    Text(state.stream?.name ?: "Stream")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.background,
                    titleContentColor = colors.body,
                    navigationIconContentColor = colors.body,
                    actionIconContentColor = colors.body,
                ),
                navigationIcon = {
                    IconButton(onClick = { onAction(StreamIntent.NavigateBack) }) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.chevron_left),
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            StreamVideoPlayer(
                playbackUrl = state.stream?.playbackUrl,
                modifier = Modifier.fillMaxWidth(),
            )

            Row {
                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                    StreamAvatar(
                        avatarUrl = state.stream?.avatarUrl,
                        name = state.stream?.name,
                    )
                }

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text = state.stream?.name ?: "Loading stream...",
                        color = colors.body,
                        fontWeight = FontWeight.SemiBold,
                    )
                    state.stream?.category?.let { category ->
                        Text(
                            text = category,
                            color = colors.bodyMuted,
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    text = "Comments",
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            2.dp,
                            colors.primary60,
                            RoundedCornerShape(16.dp),
                        ),
                    value = state.commentInput,
                    onValueChange = { onAction(StreamIntent.SetComment(it)) },
                    placeholder = {
                        Text("Write a comment...")
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = colors.backgroundDarker,
                        focusedContainerColor = colors.backgroundDarker,
                        unfocusedBorderColor = colors.background,
                        focusedBorderColor = colors.background,
                        unfocusedTextColor = colors.body,
                        focusedTextColor = colors.body,
                    ),
                )
                IconButton(
                    onClick = { },
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.icon_chat),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
private fun StreamAvatar(
    avatarUrl: String?,
    name: String?,
) {
    val colors = LocalTalkfrlyColors.current

    Surface(
        modifier = Modifier.size(40.dp),
        shape = RoundedCornerShape(999.dp),
        color = colors.backgroundDarker,
    ) {
        if (!avatarUrl.isNullOrBlank()) {
            Image(
                painter = rememberAsyncImagePainter(model = avatarUrl),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Crop,
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    text = name?.firstOrNull()?.uppercase() ?: "?",
                    color = colors.body,
                    modifier = Modifier.padding(11.dp),
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}
