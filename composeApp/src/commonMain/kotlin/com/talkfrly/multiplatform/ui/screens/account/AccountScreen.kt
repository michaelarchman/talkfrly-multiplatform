package com.talkfrly.multiplatform.ui.screens.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.Alignment
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
fun AccountScreenRoot(
    viewModel: AccountViewModel = koinViewModel(),
    navController: NavController,
    onLogout: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    AccountScreen(
        state = state,
        navController = navController,
        onAction = { intent ->
            viewModel.onIntent(intent, onLogoutSuccess = { onLogout() })
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountScreen(
    state: AccountState,
    navController: NavController,
    onAction: (AccountIntent) -> Unit,
) {
    Scaffold(
        containerColor = LocalTalkfrlyColors.current.background,
        contentColor = LocalTalkfrlyColors.current.body,
        topBar = {
            TopAppBar(
                title = { Text("Talkfrly") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalTalkfrlyColors.current.background,
                    titleContentColor = LocalTalkfrlyColors.current.body,
                    navigationIconContentColor = LocalTalkfrlyColors.current.body,
                    actionIconContentColor = LocalTalkfrlyColors.current.body,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        enabled = true,
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.chevron_left),
                            contentDescription = null,
                        )
                    }
                },
//                actions = {
//                    if (navController.currentBackStackEntry?.destination?.route == Route.Account.id) {
//                        IconButton(
//                            onClick = { navController.navigate(Route.Account.id) },
//                            enabled = true,
//                        ) {
//                            Icon(
//                                imageVector = vectorResource(Res.drawable.person),
//                                contentDescription = null,
//                            )
//                        }
//                    }
//                }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = state.message)

            Button(
                onClick = { onAction(AccountIntent.Logout) },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Logout")
            }
        }
    }

}