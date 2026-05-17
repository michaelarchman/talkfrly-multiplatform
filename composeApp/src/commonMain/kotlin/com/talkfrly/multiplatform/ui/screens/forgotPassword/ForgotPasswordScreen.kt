package com.talkfrly.multiplatform.ui.screens.forgotPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.ui.components.buttons.ButtonPrimary
import com.talkfrly.multiplatform.ui.components.buttons.ButtonSizeType
import com.talkfrly.multiplatform.ui.components.inputs.InputText
import com.talkfrly.multiplatform.ui.nav.ResetPasswordRoute
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.imageResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_dark
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_light

@Composable
fun ForgotPasswordScreenRoot(
    viewModel: ForgotPasswordViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.navigate(ResetPasswordRoute(state.email))
        }
    }

    ForgotPasswordScreen(
        state = state,
        onAction = { viewModel.onIntent(it) },
        onBack = { navController.popBackStack() }
    )
}

@Composable
private fun ForgotPasswordScreen(
    state: ForgotPasswordState,
    onAction: (ForgotPasswordIntent) -> Unit,
    onBack: () -> Unit,
) {
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
            text = "Forgot your password?",
            textAlign = TextAlign.Center,
        )

        Text(
            text = "Enter your email and we'll send you a reset code",
            textAlign = TextAlign.Center,
            color = LocalTalkfrlyColors.current.bodyMuted
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = LocalTalkfrlyColors.current.surface
        )

        InputText(
            value = state.email,
            placeholder = "Your e-mail",
            onValueChange = { onAction(ForgotPasswordIntent.UpdateEmail(it)) },
            label = "E-mail",
            modifier = Modifier.fillMaxWidth()
        )

        if (state.message != null) {
            Text(
                text = state.message,
                color = LocalTalkfrlyColors.current.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        ButtonPrimary(
            text = if (state.isLoading) "Sending..." else "Send reset code",
            enabled = state.email.isNotEmpty() && !state.isLoading,
            size = ButtonSizeType.LARGE,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = { onAction(ForgotPasswordIntent.Submit) }
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = LocalTalkfrlyColors.current.surface
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Remember your password?",
                textAlign = TextAlign.Center,
                color = LocalTalkfrlyColors.current.bodyMuted
            )
            TextButton(onClick = onBack) {
                Text(
                    text = "Log in",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(800),
                    color = LocalTalkfrlyColors.current.body,
                )
            }
        }
    }
}
