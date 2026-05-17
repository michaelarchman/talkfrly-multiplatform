package com.talkfrly.multiplatform.ui.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.talkfrly.multiplatform.AppInfo
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left
import talkfrly_multiplatform.composeapp.generated.resources.person

@Composable
fun AccountScreenRoot(
    viewModel: AccountViewModel = koinViewModel(),
    navController: NavController,
    onLogout: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(AccountIntent.GetUser)
    }

    AccountScreen(
        state = state,
        onBackClick = { navController.popBackStack() },
        onAction = { intent -> viewModel.onIntent(intent, onLogoutSuccess = { onLogout() }) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountScreen(
    state: AccountState,
    onBackClick: () -> Unit,
    onAction: (AccountIntent) -> Unit,
) {
    val colors = LocalTalkfrlyColors.current
    val uriHandler = LocalUriHandler.current

    Scaffold(
        containerColor = colors.background,
        contentColor = colors.body,
        topBar = {
            TopAppBar(
                title = { Text("Account") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.background,
                    titleContentColor = colors.body,
                    navigationIconContentColor = colors.body,
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (state.user?.avatarUrl != null) {
                    AsyncImage(
                        modifier = Modifier
                            .size(80.dp)
                            .border(2.dp, colors.primary60, RoundedCornerShape(8.dp)),
                        model = state.user.avatarUrl,
                        contentDescription = null,
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .size(80.dp)
                            .border(2.dp, colors.primary60, RoundedCornerShape(8.dp)),
                        imageVector = vectorResource(Res.drawable.person),
                        contentDescription = null,
                    )
                }

                state.user?.let { user ->
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = user.displayName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.body,
                        )
                        Text(
                            text = user.email,
                            fontSize = 14.sp,
                            color = colors.bodyMuted,
                        )
                    }
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onAction(AccountIntent.Logout) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.backgroundDarker,
                    contentColor = colors.body,
                ),
            ) {
                Text("Logout")
            }

            TextButton(
                onClick = { uriHandler.openUri("https://talkfrly.com/privacy-policy") },
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "Privacy Policy",
                    color = colors.bodyMuted,
                )
            }

            Text(
                text = "v${AppInfo.VERSION} (${AppInfo.BUILD})",
                fontSize = 10.sp,
                color = colors.bodyMuted,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}
