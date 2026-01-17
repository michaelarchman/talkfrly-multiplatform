package com.talkfrly.multiplatform.ui.compontents.inputs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.painterResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.icon_visibility_off
import talkfrly_multiplatform.composeapp.generated.resources.icon_visibility_on

@Composable
fun InputText(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String? = null,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val visualTransformation =
        if (isPassword && !passwordVisible) PasswordVisualTransformation()
        else VisualTransformation.None

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = {
            placeholder?.let { Text(it, color = LocalTalkfrlyColors.current.body60) }
        },
        shape = RoundedCornerShape(8.dp),
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = LocalTalkfrlyColors.current.primary,
            unfocusedBorderColor = LocalTalkfrlyColors.current.primary20,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = LocalTalkfrlyColors.current.surface30,
            cursorColor = LocalTalkfrlyColors.current.body80,
            focusedTextColor = LocalTalkfrlyColors.current.body,
            unfocusedTextColor = LocalTalkfrlyColors.current.body80,
            errorBorderColor = LocalTalkfrlyColors.current.error,
            errorContainerColor = LocalTalkfrlyColors.current.error20,
            focusedLabelColor = LocalTalkfrlyColors.current.primary,
            unfocusedLabelColor = LocalTalkfrlyColors.current.body80
        ),
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
        ),
        singleLine = true,
        trailingIcon = {
            if (isPassword) {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible }
                ) {
                    val icon = if (passwordVisible)
                        Res.drawable.icon_visibility_on
                    else
                        Res.drawable.icon_visibility_off
                    Icon(
                        painterResource(icon),
                        tint = LocalTalkfrlyColors.current.body60,
                        contentDescription = null
                    )
                }
            }
        },
        supportingText = {
            if (isError && errorMessage != null) {
                Text(
                    errorMessage,
                    color = LocalTalkfrlyColors.current.error
                )
            }
        }
    )
}
