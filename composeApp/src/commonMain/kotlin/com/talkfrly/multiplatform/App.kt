package com.talkfrly.multiplatform

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.talkfrly.multiplatform.ui.compontents.bars.Topbar
import com.talkfrly.multiplatform.ui.nav.AppNavHost
import com.talkfrly.multiplatform.ui.session.Session
import com.talkfrly.multiplatform.ui.session.SessionViewModel
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import com.talkfrly.multiplatform.ui.theme.TalkfrlyTheme
import org.koin.compose.viewmodel.koinViewModel
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_dark
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_light

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    viewModel: AppViewModel = koinViewModel<AppViewModel>()
) {
    val sessionViewModel: SessionViewModel = koinViewModel<SessionViewModel>()
    val navController = rememberNavController()
    val globalLoadingCount by BaseViewModel.globalLoadingCount.collectAsState()
    val isGlobalLoading = globalLoadingCount > 0

    TalkfrlyTheme {
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
                    }
                )
            },
            containerColor = LocalTalkfrlyColors.current.background,
            contentColor = LocalTalkfrlyColors.current.body,
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.systemBars
        ) { _ ->
            Box(modifier = Modifier.fillMaxSize()) {
                Session(
                    sessionViewModel,
                    navController
                ) {
                    AppNavHost(
                        navController = navController,
                        sessionViewModel = sessionViewModel
                    )
                }

                if (isGlobalLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(LocalTalkfrlyColors.current.background.copy(alpha = 0.9f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                bitmap = if (isSystemInDarkTheme())
                                    imageResource(Res.drawable.talkfrly_logo_dark)
                                else imageResource(Res.drawable.talkfrly_logo_light),
                                contentDescription = null,
                                modifier = Modifier.size(140.dp)
                            )
                            CircularProgressIndicator(
                                color = LocalTalkfrlyColors.current.primary,
                                trackColor = LocalTalkfrlyColors.current.surface
                            )
                        }
                    }
                }
            }
        }
    }
}
