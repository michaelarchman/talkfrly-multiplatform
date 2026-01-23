package com.talkfrly.multiplatform.ui.compontents.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left

@Composable
fun Topbar(
    title: String,
    canPopBack: Boolean,
    onPopBack: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(LocalTalkfrlyColors.current.backgroundLighter)
    ) {
        Icon(vectorResource(Res.drawable.chevron_left), null)
        Text(title)
    }
}