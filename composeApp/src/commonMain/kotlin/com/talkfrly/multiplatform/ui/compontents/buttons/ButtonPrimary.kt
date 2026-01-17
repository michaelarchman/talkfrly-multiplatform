package com.talkfrly.multiplatform.ui.compontents.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

enum class ButtonSizeType {
    SMALL, MEDIUM, LARGE
}

@Composable
fun ButtonPrimary(
    text: String,
    size: ButtonSizeType = ButtonSizeType.MEDIUM,
    enabled: Boolean? = true,
    elevation: Boolean? = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val padding = when (size) {
        ButtonSizeType.SMALL -> 0.dp
        ButtonSizeType.MEDIUM -> 4.dp
        ButtonSizeType.LARGE -> 8.dp
    }

    Button(
        enabled = enabled == true,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = LocalTalkfrlyColors.current.primary,
            contentColor = LocalTalkfrlyColors.current.background,
        ),
        shape = RoundedCornerShape(8.dp),
        elevation =
            if (elevation == true) ButtonDefaults.buttonElevation(4.dp)
            else null,
        onClick = onClick,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(padding),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(700),
            fontSize = 16.sp
        )
    }
}