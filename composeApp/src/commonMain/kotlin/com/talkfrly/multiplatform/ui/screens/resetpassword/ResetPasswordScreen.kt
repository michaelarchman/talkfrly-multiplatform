package com.talkfrly.multiplatform.ui.screens.resetpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.ui.components.buttons.ButtonPrimary
import com.talkfrly.multiplatform.ui.components.buttons.ButtonSizeType
import com.talkfrly.multiplatform.ui.components.inputs.InputText
import com.talkfrly.multiplatform.ui.nav.LoginRoute
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.imageResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_dark
import talkfrly_multiplatform.composeapp.generated.resources.talkfrly_logo_light

@Composable
fun ResetPasswordScreenRoot(
    email: String = "",
    viewModel: ResetPasswordViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    if (email.isNotEmpty() && state.email.isEmpty()) {
        viewModel.initializeEmail(email)
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.navigate(LoginRoute) {
                popUpTo(LoginRoute) { inclusive = true }
            }
        }
    }

    ResetPasswordScreen(
        state = state,
        onAction = { viewModel.onIntent(it) },
    )
}

@Composable
private fun ResetPasswordScreen(
    state: ResetPasswordState,
    onAction: (ResetPasswordIntent) -> Unit,
) {
    val code = state.code
    val codeChars = code.padEnd(6, ' ').take(6)
    val focusRequesters = remember { List(6) { FocusRequester() } }

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
            text = "Reset your password",
            textAlign = TextAlign.Center,
        )

        Text(
            text = "Enter the code sent to\n${state.email}",
            textAlign = TextAlign.Center,
            color = LocalTalkfrlyColors.current.bodyMuted
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = LocalTalkfrlyColors.current.surface
        )

        if (state.message != null) {
            Text(
                text = state.message,
                color = if (state.isSuccess)
                    LocalTalkfrlyColors.current.primary
                else
                    LocalTalkfrlyColors.current.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(6) { index ->
                val currentChar = codeChars[index].takeIf { it != ' ' }?.toString() ?: ""
                OutlinedTextField(
                    value = currentChar,
                    onValueChange = { value ->
                        val digits = value.filter { it.isDigit() }
                        val updated = buildString {
                            repeat(6) { i ->
                                val relative = i - index
                                when {
                                    digits.length > 1 && relative in digits.indices ->
                                        append(digits[relative])
                                    i == index && digits.isNotEmpty() ->
                                        append(digits.first())
                                    i == index && digits.isEmpty() ->
                                        append(' ')
                                    else ->
                                        append(codeChars[i])
                                }
                            }
                        }.replace(" ", "")

                        onAction(ResetPasswordIntent.UpdateCode(updated))

                        when {
                            digits.length > 1 -> {
                                val nextIndex = (index + digits.length).coerceAtMost(5)
                                focusRequesters[nextIndex].requestFocus()
                            }
                            digits.isNotEmpty() && index < 5 -> {
                                focusRequesters[index + 1].requestFocus()
                            }
                            digits.isEmpty() && index > 0 -> {
                                focusRequesters[index - 1].requestFocus()
                            }
                        }
                    },
                    singleLine = true,
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .width(48.dp)
                        .focusRequester(focusRequesters[index]),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LocalTalkfrlyColors.current.primary,
                        unfocusedBorderColor = LocalTalkfrlyColors.current.surface,
                        focusedTextColor = LocalTalkfrlyColors.current.body,
                        unfocusedTextColor = LocalTalkfrlyColors.current.body,
                        cursorColor = LocalTalkfrlyColors.current.primary
                    )
                )
            }
        }

        InputText(
            value = state.newPassword,
            placeholder = "New password",
            onValueChange = { onAction(ResetPasswordIntent.UpdateNewPassword(it)) },
            label = "New password",
            isPassword = true,
            modifier = Modifier.fillMaxWidth()
        )

        InputText(
            value = state.confirmPassword,
            placeholder = "Confirm new password",
            onValueChange = { onAction(ResetPasswordIntent.UpdateConfirmPassword(it)) },
            label = "Confirm password",
            isPassword = true,
            modifier = Modifier.fillMaxWidth()
        )

        ButtonPrimary(
            text = if (state.isLoading) "Resetting..." else "Reset password",
            enabled = code.length == 6 && state.newPassword.isNotEmpty() && !state.isLoading,
            size = ButtonSizeType.LARGE,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = { onAction(ResetPasswordIntent.Submit) }
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
                onClick = { onAction(ResetPasswordIntent.ResendCode) },
                enabled = state.resendCooldown == 0 && !state.resendLoading,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text(
                    text = when {
                        state.resendCooldown > 0 -> "Resend in ${state.resendCooldown}s"
                        state.resendLoading -> "Sending..."
                        else -> "Resend code"
                    },
                    color = LocalTalkfrlyColors.current.body
                )
            }
        }
    }
}
