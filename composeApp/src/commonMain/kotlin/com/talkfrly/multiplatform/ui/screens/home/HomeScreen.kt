package com.talkfrly.multiplatform.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.domain.feed.FeedItem
import com.talkfrly.multiplatform.ui.Route
import com.talkfrly.multiplatform.ui.components.feeds.FeedCard
import com.talkfrly.multiplatform.ui.components.streams.StreamCard
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.add
import talkfrly_multiplatform.composeapp.generated.resources.follow_the_signs
import talkfrly_multiplatform.composeapp.generated.resources.gesture
import talkfrly_multiplatform.composeapp.generated.resources.person
import talkfrly_multiplatform.composeapp.generated.resources.speed_camera
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_dark
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_light
import talkfrly_multiplatform.composeapp.generated.resources.travel_explore

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(HomeIntent.GetFeeds)
        viewModel.onIntent(HomeIntent.GetStreams)
    }

    HomeScreen(
        state = state,
        navController = navController,
        onAction = { intent ->
            viewModel.onIntent(intent)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeState,
    navController: NavController,
    onAction: (HomeIntent) -> Unit,
) {
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
                    if (navController.currentBackStackEntry?.destination?.route == Route.Home.id) {
                        IconButton(
                            onClick = { navController.navigate(Route.CreatePublication()) },
                            enabled = true,
                        ) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.add),
                                contentDescription = "Create publication",
                            )
                        }
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
                .padding(paddingValues),
            horizontalAlignment = Alignment.Start
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
                                    imageVector = vectorResource(Res.drawable.gesture),
                                    contentDescription = null,
                                    Modifier.size(18.dp).padding(end = 4.dp)
                                )
                                Text(text = "Threads", fontWeight = FontWeight(600))
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
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = vectorResource(Res.drawable.speed_camera),
                                    contentDescription = null,
                                    Modifier.size(18.dp).padding(end = 4.dp)
                                )
                                Text(
                                    text = "Streams",
                                    fontWeight = FontWeight(600),
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
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = vectorResource(Res.drawable.follow_the_signs),
                                    contentDescription = null,
                                    Modifier.size(18.dp).padding(end = 4.dp)
                                )
                                Text(text = "Followed", fontWeight = FontWeight(600))
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
                    FeedTabContent(feedItems = state.feeds?.feed.orEmpty())
                }
                1 -> {
                    StreamsTabContent(
                        state = state,
                        onStreamClick = { streamId ->
                            navController.navigate(Route.Stream(streamId))
                        },
                    )
                }
                2 -> {
                   Text("Followed stuff")
                }
            }
        }
    }
}

@Composable
private fun FeedTabContent(
    feedItems: List<FeedItem>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(feedItems) { feedItem ->
            FeedCard(feedItem = feedItem)
        }
    }
}

@Composable
private fun StreamsTabContent(
    state: HomeState,
    onStreamClick: (String) -> Unit,
) {
    if (state.isLoadingStreams && state.streams.isEmpty()) {
        Text("Loading streams...")
    }
    else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.streams, key = { it.id }) { stream ->
                StreamCard(
                    stream = stream,
                    onClick = { onStreamClick(stream.id) },
                )
            }
        }
    }
}