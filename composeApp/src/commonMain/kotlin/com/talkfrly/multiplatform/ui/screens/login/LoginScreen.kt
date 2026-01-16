package com.talkfrly.multiplatform.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.talkfrly.multiplatform.ui.compontents.inputs.InputText
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LoginScreen(
        state = state,
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
    onAction: (LoginIntent) -> Unit,
) {
    val username = state.email
    val password = state.password
    val message = state.message

    Column(
        modifier = Modifier.padding(28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Witaj,",
            textAlign = TextAlign.Start,
        )
        Text(
            text = "zaloguj się używając danych przekazanych przez Administratora.",
            textAlign = TextAlign.Start,
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
        )
        InputText(
            value = username ?: "",
            placeholder = "Nazwa użytkownika",
            onValueChange = { onAction(LoginIntent.UpdateFieldUsername(it)) },
            label = "",
            modifier = Modifier.fillMaxWidth()
        )
        InputText(
            value = password ?: "",
            placeholder = "Hasło",
            onValueChange = { onAction(LoginIntent.UpdateFieldPassword(it)) },
            label = "",
            isPassword = true,
            modifier = Modifier.fillMaxWidth()
        )
//        ButtonPrimary(
//            enabled = !domainError,
//            text = "Zaloguj",
//            modifier = Modifier.fillMaxWidth(),
//            onClick = {
//                onAction(LoginIntent.GetToken)
//            }
//        )
    }
}