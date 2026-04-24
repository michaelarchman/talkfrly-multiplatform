package com.talkfrly.multiplatform.ui.screens.publication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.talkfrly.multiplatform.ui.components.bars.BottomBarInput
import com.talkfrly.multiplatform.ui.components.feed.FeedAvatar
import com.talkfrly.multiplatform.ui.screens.splash.SplashScreen
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicationScreenRoot(
    publicationId: String,
    viewModel: PublicationScreenViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()
    val loadingCount by viewModel.loadingCount.collectAsState()

    LaunchedEffect(publicationId) {
        viewModel.initialize(publicationId)
    }

    LaunchedEffect(Unit) {
        viewModel.onIntent(PublicationScreenIntent.GetPublications)
        viewModel.onIntent(PublicationScreenIntent.GetComments)
    }

    Scaffold(
        containerColor = LocalTalkfrlyColors.current.background,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.publication?.user?.let {
                            FeedAvatar(
                                avatarUrl = state.publication!!.avatarUrl,
                                label = it.displayName ?: "Anonymous",
                            )

                            Text(
                                text = it.displayName ?: "Anonymous",
                                color = LocalTalkfrlyColors.current.body,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
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
        },
        bottomBar = {
            BottomBarInput(
                value = state.commentFormContent,
                onValueChange = { },
                onSendClick = { },
                placeholder = "Write a comment...",
            )
        }
    ) {
        LazyColumn(
            Modifier.padding(it)
        ) {
            item {
                PublicationScreen(
                    state = state,
                    isLoading = loadingCount > 0,
                    onAction = { intent -> viewModel.onIntent(intent)}
                )
            }

        }
    }
}

@Composable
private fun PublicationScreen(
    state: PublicationScreenState,
    isLoading: Boolean,
    onAction: (PublicationScreenIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {


        /**
         * Publication content
         */
        if (state.publication == null) {
            if (isLoading) {
                SplashScreen()
            } else {
                Text(
                    text = "No publication loaded. Try again.",
                    color = LocalTalkfrlyColors.current.body,
                )
            }
            return
        }

        state.publication.content.let { pub ->
            val lines = pub.lines()
            val headerLine = lines.firstOrNull { it.startsWith("#") }
            val bodyText = lines.firstOrNull { !it.startsWith("#") && it.isNotBlank() }.orEmpty()

            if (headerLine != null) {
                Text(
                    text = headerLine.removePrefix("#").trimStart(),
                    color = LocalTalkfrlyColors.current.body,
                    fontSize = 18.sp,
                    letterSpacing = 0.8.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Text(
                text = bodyText,
                color = LocalTalkfrlyColors.current.body,
                fontSize = 16.sp,
                letterSpacing = 0.8.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Light,
            )
        }


        state.publication.imageUrls.let {
            Image(
                modifier = Modifier.clip(ShapeDefaults.ExtraSmall),
                painter = rememberAsyncImagePainter(model = it.first()),
                contentDescription = "Publication picture",
                contentScale = ContentScale.FillWidth,
            )
        }

        /**
         * Comments
         */
        Text(
            text = "Comments (${state.publication.commentCount})",
            color = LocalTalkfrlyColors.current.body,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )

        if (state.comments.isEmpty()) {
            Text(
                text = "No comments yet. Be first!",
                color = LocalTalkfrlyColors.current.bodyMuted,
            )
        }

        state.comments.forEach {
            Column {
                Text(
                    text = it.content,
                    color = LocalTalkfrlyColors.current.body,
                )
                Text(
                    text = it.user?.displayName ?: "Anonymous",
                    color = LocalTalkfrlyColors.current.body
                )
            }
        }
    }
}