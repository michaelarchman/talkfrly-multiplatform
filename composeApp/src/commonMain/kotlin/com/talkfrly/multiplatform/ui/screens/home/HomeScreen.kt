package com.talkfrly.multiplatform.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.talkfrly.multiplatform.ui.Route
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.follow_the_signs
import talkfrly_multiplatform.composeapp.generated.resources.gesture
import talkfrly_multiplatform.composeapp.generated.resources.icon_visibility_on
import talkfrly_multiplatform.composeapp.generated.resources.person
import talkfrly_multiplatform.composeapp.generated.resources.speed_camera
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_dark
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_light
import talkfrly_multiplatform.composeapp.generated.resources.texture_add
import kotlin.math.absoluteValue

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
                title = {
                    Image(
                        painter = painterResource(Res.drawable.talkfrly_logo_dark),
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
//                navigationIcon = {
//                    IconButton(
//                        onClick = {},
//                        enabled = true,
//                    ) {
//                        Icon(
//                            imageVector = vectorResource(Res.drawable.chevron_left),
//                            contentDescription = null,
//                        )
//                    }
//                },
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
                            selectedContentColor = LocalTalkfrlyColors.current.primary,
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
                            selectedContentColor = LocalTalkfrlyColors.current.primary,
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
                            selectedContentColor = LocalTalkfrlyColors.current.primary,
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
                        imageVector = vectorResource(Res.drawable.texture_add),
                        tint = LocalTalkfrlyColors.current.body,
                        contentDescription = null,
                    )
                }
            }

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
                                publication.imageUrls.firstOrNull()?.let { imageUrl ->
                                    Image(
                                        painter = rememberAsyncImagePainter(model = imageUrl),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(300.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }

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