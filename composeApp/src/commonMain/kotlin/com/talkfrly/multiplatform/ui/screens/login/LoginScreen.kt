package com.talkfrly.multiplatform.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.talkfrly.multiplatform.ui.compontents.buttons.ButtonPrimary
import com.talkfrly.multiplatform.ui.compontents.buttons.ButtonSizeType
import com.talkfrly.multiplatform.ui.compontents.inputs.InputText
import org.jetbrains.compose.resources.imageResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_dark
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_light

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LoginScreen(
        state = state,
        modifier = modifier,
        onAction = { intent ->
            viewModel.onIntent(intent) {
                onLoginSuccess()
            }
        }
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    modifier: Modifier = Modifier,
    onAction: (LoginIntent) -> Unit,
) {
    val username = state.email
    val password = state.password
    val message = state.message

    Column(
        modifier = modifier
            .padding(32.dp)
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
        ButtonPrimary(
            text = "Login",
            enabled = true,
            size = ButtonSizeType.LARGE,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            onClick = { }
        )
    }
}