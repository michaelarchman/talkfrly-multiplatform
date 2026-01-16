package com.talkfrly.multiplatform.ui.compontents.inputs

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
import org.jetbrains.compose.resources.painterResource

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
        label = {
            Text(
                label,
//                style = LocalChronoTypography.current.bodyMedium
            )
        },
        placeholder = {
            placeholder?.let {
                Text(
                    it,
//                    style = LocalChronoTypography.current.bodyMedium,
//                    color = LocalChronoColors.current.onBackgroundContainer
                )
            }
        },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = LocalChronoColors.current.primary,
//            unfocusedBorderColor = Color.Transparent,
//            focusedContainerColor = LocalChronoColors.current.white,
//            unfocusedContainerColor = LocalChronoColors.current.white,
//            cursorColor = LocalChronoColors.current.primary,
//            focusedTextColor = LocalChronoColors.current.black,
//            unfocusedTextColor = LocalChronoColors.current.black,
//            errorBorderColor = LocalChronoColors.current.error,
//            errorContainerColor = LocalChronoColors.current.errorSurface,
//        ),
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text
        ),
        singleLine = true,
        trailingIcon = {
            if (isPassword) {
//                IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                    val icon = if (passwordVisible)
//                        chrono2_android.composeapp.generated.resources.Res.drawable.icon_show_password
//                    else
//                        chrono2_android.composeapp.generated.resources.Res.drawable.icon_show_password
//                    Icon(painterResource(icon), contentDescription = null)
//                }
            }
        },
        supportingText = {
            if (isError && errorMessage != null) {
                Text(
                    errorMessage,
//                    color = LocalChronoColors.current.error
                )
            }
        }
    )
}
