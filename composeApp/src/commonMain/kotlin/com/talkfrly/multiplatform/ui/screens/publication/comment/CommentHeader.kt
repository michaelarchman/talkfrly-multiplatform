package com.talkfrly.multiplatform.ui.screens.publication.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

@Composable
fun CommentsHeader(commentCount: Int, isEmpty: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Comments ($commentCount)",
            color = LocalTalkfrlyColors.current.body,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
        )

        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp),
            color = LocalTalkfrlyColors.current.primary20,
        )

        if (isEmpty) {
            Text(
                text = "No comments yet. Be first!",
                color = LocalTalkfrlyColors.current.bodyMuted,
            )
        }
    }
}
