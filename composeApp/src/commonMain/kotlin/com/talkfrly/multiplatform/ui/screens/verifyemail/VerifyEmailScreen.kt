package com.talkfrly.multiplatform.ui.screens.verifyemail

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.ui.compontents.buttons.ButtonPrimary
import com.talkfrly.multiplatform.ui.compontents.buttons.ButtonSizeType
import com.talkfrly.multiplatform.ui.compontents.inputs.InputText
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.imageResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_dark
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_light

@Composable
fun VerifyEmailScreenRoot(
    email: String = "",
    viewModel: VerifyEmailViewModel = koinViewModel(),
    navController: NavController,
    onVerifySuccess: (() -> Unit)? = null,
) {
    val state by viewModel.state.collectAsState()

    // Initialize email if provided
    if (email.isNotEmpty() && state.email.isEmpty()) {
        viewModel.initializeEmail(email)
    }

    VerifyEmailScreen(
        state = state,
        navController = navController,
        onAction = { intent ->
            viewModel.onIntent(intent)
        },
        onVerifySuccess = onVerifySuccess
    )
}

@Composable
private fun VerifyEmailScreen(
    state: VerifyEmailState,
    navController: NavController,
    onAction: (VerifyEmailIntent) -> Unit,
    onVerifySuccess: (() -> Unit)? = null,
) {
    val email = state.email
    val code = state.code
    val message = state.message
    val isLoading = state.isLoading
    val resendLoading = state.resendLoading
    val resendCooldown = state.resendCooldown

    // Check if verification was successful
    if (message?.contains("verified", ignoreCase = true) == true) {
        onVerifySuccess?.invoke()
    }

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
            text = "Verify your email",
            textAlign = TextAlign.Center,
        )

        Text(
            text = "We sent a code to\n$email",
            textAlign = TextAlign.Center,
            color = LocalTalkfrlyColors.current.bodyMuted
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = LocalTalkfrlyColors.current.surface
        )

        if (message != null) {
            Text(
                text = message,
                color = if (message.contains("verified", ignoreCase = true))
                    LocalTalkfrlyColors.current.primary
                else
                    LocalTalkfrlyColors.current.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        InputText(
            value = code,
            placeholder = "000000",
            onValueChange = { onAction(VerifyEmailIntent.UpdateCode(it)) },
            label = "Verification code",
            modifier = Modifier.fillMaxWidth()
        )

        ButtonPrimary(
            text = if (isLoading) "Verifying..." else "Verify",
            enabled = code.length == 6 && !isLoading,
            size = ButtonSizeType.LARGE,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = { onAction(VerifyEmailIntent.VerifyCode) }
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = LocalTalkfrlyColors.current.surface
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Didn't receive the code?",
                textAlign = TextAlign.Center,
                color = LocalTalkfrlyColors.current.bodyMuted
            )

            TextButton(
                onClick = { onAction(VerifyEmailIntent.ResendCode) },
                enabled = resendCooldown == 0 && !resendLoading,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = if (resendCooldown > 0)
                        "Resend in ${resendCooldown}s"
                    else if (resendLoading)
                        "Sending..."
                    else
                        "Resend code",
                    color = LocalTalkfrlyColors.current.body
                )
            }
        }
    }
}