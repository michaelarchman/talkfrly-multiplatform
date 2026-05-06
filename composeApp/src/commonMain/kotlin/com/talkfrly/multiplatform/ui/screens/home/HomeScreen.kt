package com.talkfrly.multiplatform.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.talkfrly.multiplatform.ui.nav.AccountRoute
import com.talkfrly.multiplatform.ui.nav.NewPublicationRoute
import com.talkfrly.multiplatform.ui.nav.PublicationRoute
import com.talkfrly.multiplatform.ui.screens.home.feed.FeedTab
import com.talkfrly.multiplatform.ui.screens.home.threads.ThreadsTab
import com.talkfrly.multiplatform.ui.screens.home.streams.StreamsTab
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.forum
import talkfrly_multiplatform.composeapp.generated.resources.icon_add_ad
import talkfrly_multiplatform.composeapp.generated.resources.person
import talkfrly_multiplatform.composeapp.generated.resources.siren_open
import talkfrly_multiplatform.composeapp.generated.resources.speed_camera
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_dark
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_light
import talkfrly_multiplatform.composeapp.generated.resources.travel_explore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(HomeIntent.GetCurrentUser)
        viewModel.onIntent(HomeIntent.GetStreams)
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(
                            if (isSystemInDarkTheme())
                                Res.drawable.talkfrly_logo_dark
                            else Res.drawable.talkfrly_logo_light
                        ),
                        contentDescription = "Talkfrly logo",
                        modifier = Modifier.width(108.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalTalkfrlyColors.current.background,
                    titleContentColor = LocalTalkfrlyColors.current.body,
                    navigationIconContentColor = LocalTalkfrlyColors.current.body,
                    actionIconContentColor = LocalTalkfrlyColors.current.body,
                ),
                actions = {
                    IconButton(
                        onClick = { navController.navigate(NewPublicationRoute) },
                        enabled = true,
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.icon_add_ad),
                            contentDescription = "Create publication",
                        )
                    }

                    IconButton(
                        onClick = { navController.navigate(AccountRoute) },
                        enabled = true,
                    ) {
                        if (state.currentUser?.avatarUrl != null) {
                            Image(
                                painter = rememberAsyncImagePainter(state.currentUser?.avatarUrl),
                                contentDescription = "Go to Account",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .aspectRatio(1f)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = vectorResource(Res.drawable.person),
                                contentDescription = "Go to Account",
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
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start
        ) {
            HomeScreen(
                state = state,
                navController = navController,
                onAction = { intent -> viewModel.onIntent(intent) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeState,
    navController: NavController,
    onAction: (HomeIntent) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
        ) {
            SecondaryScrollableTabRow(
                selectedTabIndex = state.selectedTabIndex,
                containerColor = LocalTalkfrlyColors.current.background,
                contentColor = LocalTalkfrlyColors.current.body,
                edgePadding = 0.dp,
                indicator = {
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(state.selectedTabIndex,
                                matchContentSize = false)
                            .height(2.dp)
                            .background(LocalTalkfrlyColors.current.primary)
                    )
                },
                divider = {},
                modifier = Modifier.height(40.dp),
            ) {
                Tab(
                    selectedContentColor = LocalTalkfrlyColors.current.body,
                    unselectedContentColor = LocalTalkfrlyColors.current.bodyMuted,
                    selected = state.selectedTabIndex == 0,
                    onClick = { onAction(HomeIntent.SetSelectedTab(0)) },
                    modifier = Modifier.height(40.dp).padding(2.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.siren_open),
                            contentDescription = null,
                            Modifier.size(24.dp).padding(end = 4.dp)
                        )
                        Text(text = "Feed", fontWeight = FontWeight(600))
                    }
                }
                Tab(
                    selectedContentColor = LocalTalkfrlyColors.current.body,
                    unselectedContentColor = LocalTalkfrlyColors.current.bodyMuted,
                    selected = state.selectedTabIndex == 1,
                    onClick = { onAction(HomeIntent.SetSelectedTab(1)) },
                    modifier = Modifier.height(40.dp).padding(2.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.forum),
                            contentDescription = null,
                            Modifier.size(24.dp).padding(end = 4.dp)
                        )
                        Text(
                            text = "Threads",
                            fontWeight = FontWeight(600)
                        )
                    }
                }
                Tab(
                    selectedContentColor = LocalTalkfrlyColors.current.body,
                    unselectedContentColor = LocalTalkfrlyColors.current.bodyMuted,
                    selected = state.selectedTabIndex == 2,
                    onClick = { onAction(HomeIntent.SetSelectedTab(2)) },
                    modifier = Modifier.height(40.dp).padding(2.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.speed_camera),
                            contentDescription = null,
                            Modifier.size(24.dp).padding(end = 4.dp)
                        )
                        Text(
                            text = "Streams",
                            fontWeight = FontWeight(600),
                        )
                    }
                }
            }
        }

        IconButton(
            onClick = { },
            enabled = true,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = LocalTalkfrlyColors.current.body
            ),
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.travel_explore),
                tint = LocalTalkfrlyColors.current.body,
                contentDescription = null,
            )
        }
    }

    when (state.selectedTabIndex) {
        0 -> {
            FeedTab(
                onFeedItemClick = { feedItem ->
                    navController.navigate(PublicationRoute(feedItem.id))
                }
            )
        }
        1 -> {
            ThreadsTab()
        }
        2 -> {
            StreamsTab(
                onCategoryClick = { },
            )
        }
    }
}

