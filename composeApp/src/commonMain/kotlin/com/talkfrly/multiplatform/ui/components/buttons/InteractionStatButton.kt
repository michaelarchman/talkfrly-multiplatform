package com.talkfrly.multiplatform.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

enum class InteractionStatButtonType {
    OUTLINED, SIMPLE
}

@Composable
fun InteractionStatButton(
    isActive: Boolean,
    type: InteractionStatButtonType,
    icon: DrawableResource,
    label: Int,
    onClick: (() -> Unit)? = null,
) {
    val colors = LocalTalkfrlyColors.current

    OutlinedButton(
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isActive) colors.primary60 else Color.Transparent,
        ),
        enabled = true,
        border = if (type == InteractionStatButtonType.OUTLINED) {
            BorderStroke(
                width = 1.dp,
                color = if (isActive) colors.primary60 else colors.primary20,
            )
        } else null,
        contentPadding = PaddingValues(horizontal = 4.dp),
        onClick = { onClick?.invoke() },
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = vectorResource(icon),
            contentDescription = "feed state $label",
            tint = if (type == InteractionStatButtonType.OUTLINED) colors.body else colors.bodyMuted,
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = label.toString(),
            color = if (type == InteractionStatButtonType.OUTLINED) {
                if (isActive) colors.body else colors.bodyMuted
            } else colors.bodyMuted,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}
