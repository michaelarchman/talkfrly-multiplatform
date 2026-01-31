package com.talkfrly.multiplatform.ui.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.ui.Route
import com.talkfrly.multiplatform.ui.components.buttons.ButtonPrimary
import com.talkfrly.multiplatform.ui.components.buttons.ButtonSizeType
import com.talkfrly.multiplatform.ui.components.inputs.InputText
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.imageResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_dark
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_light

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    RegisterScreen(
        state = state,
        navController = navController,
        onAction = { intent ->
            viewModel.onIntent(intent) {
                navController.navigate(Route.VerifyEmail.id)
            }
        }
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    navController: NavController,
    onAction: (RegisterIntent) -> Unit,
) {
    val email = state.email
    val password = state.password
    val confirmPassword = state.confirmPassword
    val displayName = state.displayName
    val message = state.message

    val passwordsMatch = password.isNullOrBlank() || confirmPassword.isNullOrBlank() || password == confirmPassword
    val passwordValid = password.isNullOrBlank() || password.length >= 8
    val emailValid = email.isNullOrBlank() || isValidEmail(email)

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
            contentDescription = null,
            modifier = Modifier.size(128.dp),
        )
        Text(
            text = "Create your account",
            textAlign = TextAlign.Center,
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = LocalTalkfrlyColors.current.surface
        )
        if (message != null) {
            Text(
                text = message,
                color = LocalTalkfrlyColors.current.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        InputText(
            value = email ?: "",
            placeholder = "Your e-mail",
            onValueChange = { onAction(RegisterIntent.UpdateFieldEmail(it)) },
            label = "E-mail",
            isError = !emailValid,
            errorMessage = if (!email.isNullOrBlank() && !emailValid) "Please enter a valid email" else null,
            modifier = Modifier.fillMaxWidth()
        )
        InputText(
            value = displayName ?: "",
            placeholder = "Display name",
            onValueChange = { onAction(RegisterIntent.UpdateFieldDisplayName(it)) },
            label = "Display name",
            modifier = Modifier.fillMaxWidth()
        )
        InputText(
            value = password ?: "",
            placeholder = "Password",
            onValueChange = { onAction(RegisterIntent.UpdateFieldPassword(it)) },
            label = "Password",
            isPassword = true,
            isError = !passwordValid || !passwordsMatch,
            errorMessage = when {
                !password.isNullOrBlank() && password.length < 8 -> "Password must be at least 8 characters"
                !passwordsMatch -> "Passwords do not match"
                else -> null
            },
            modifier = Modifier.fillMaxWidth()
        )
        InputText(
            value = confirmPassword ?: "",
            placeholder = "Confirm password",
            onValueChange = { onAction(RegisterIntent.UpdateFieldConfirmPassword(it)) },
            label = "Confirm password",
            isPassword = true,
            isError = !passwordsMatch,
            errorMessage = if (!passwordsMatch) "Passwords do not match" else null,
            modifier = Modifier.fillMaxWidth()
        )
        ButtonPrimary(
            text = "Register account",
            enabled = true,
            size = ButtonSizeType.LARGE,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            onClick = { onAction(RegisterIntent.CreateAccount) }
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = LocalTalkfrlyColors.current.surface
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Already have an account?",
                textAlign = TextAlign.Center,
                color = LocalTalkfrlyColors.current.bodyMuted
            )
            Text(
                text = "Login",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(800),
                color = LocalTalkfrlyColors.current.body,
                modifier = Modifier.clickable { navController.navigate(Route.Login.id) }
            )

        }
    }
}

private fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    return emailRegex.matches(email)
}