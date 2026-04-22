package com.talkfrly.multiplatform.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.ui.components.buttons.ButtonPrimary
import com.talkfrly.multiplatform.ui.components.buttons.ButtonSizeType
import com.talkfrly.multiplatform.ui.components.inputs.InputText
import com.talkfrly.multiplatform.ui.nav.RegisterRoute
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.imageResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_dark
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_light

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            LoginScreen(
                state = state,
                navController = navController,
                onAction = { intent -> viewModel.onIntent(intent) }
            )
        }
    }
}

@Composable
private fun LoginScreen(
    state: LoginState,
    navController: NavController,
    onAction: (LoginIntent) -> Unit,
) {
    val username = state.email
    val password = state.password
    val message = state.message

    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            alignment = Alignment.CenterVertically,
            space = 8.dp
        )
    ) {
        Image(
            bitmap = if (isSystemInDarkTheme())
                imageResource(Res.drawable.talkfrly_logo_dark)
            else imageResource(Res.drawable.talkfrly_logo_light),
            contentDescription = null
        )
        Text(
            text = "Log in to your account",
            textAlign = TextAlign.Center,
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = LocalTalkfrlyColors.current.surface
        )
        InputText(
            value = username ?: "",
            placeholder = "Your e-mail",
            onValueChange = { onAction(LoginIntent.UpdateFieldUsername(it)) },
            label = "E-mail",
            modifier = Modifier.fillMaxWidth()
        )
        InputText(
            value = password ?: "",
            placeholder = "Password",
            onValueChange = { onAction(LoginIntent.UpdateFieldPassword(it)) },
            label = "Password",
            isPassword = true,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Forgot password",
            color = LocalTalkfrlyColors.current.bodyMuted,
            textAlign = TextAlign.Right,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight(800),
        )
        ButtonPrimary(
            text = "Login",
            enabled = true,
            size = ButtonSizeType.LARGE,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            onClick = { onAction(LoginIntent.GetAccessToken) }
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = LocalTalkfrlyColors.current.surface
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Don't have an account?",
                textAlign = TextAlign.Center,
                color = LocalTalkfrlyColors.current.bodyMuted
            )
            TextButton(
                onClick = { navController.navigate(RegisterRoute) }
            ) {
                Text(
                    text = "Sign up",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(800),
                    color = LocalTalkfrlyColors.current.body,
                )
            }

        }

        message?.let{
            Box(
                Modifier
                    .background(LocalTalkfrlyColors.current.primary.copy(.4f))
                    .padding(8.dp)
            ){
                Text(
                    text = message,
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}