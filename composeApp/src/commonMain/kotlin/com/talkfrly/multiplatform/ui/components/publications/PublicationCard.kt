package com.talkfrly.multiplatform.ui.components.publications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.talkfrly.multiplatform.domain.publication.Publication
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

@Composable
fun PublicationCard(
    publication: Publication,
    viewMode: PublicationViewMode = PublicationViewMode.FEED,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    onSeeMoreClick: (() -> Unit)? = null,
) {
    val colors = LocalTalkfrlyColors.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (viewMode == PublicationViewMode.FEED)
                colors.backgroundLighter else colors.backgroundDarker,
            contentColor = colors.body,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Header
            PublicationHeader(publication = publication)

            // Content
            PublicationContent(
                content = publication.content,
                viewMode = viewMode,
                onSeeMoreClick = onSeeMoreClick,
            )

            // Media (images, YouTube, etc.)
            PublicationMedia(
                publication = publication,
                viewMode = viewMode,
            )

            // Ratings (if present)
            if (!publication.criteria.isNullOrEmpty()) {
                HorizontalDivider(color = colors.backgroundDarker)
                PublicationRatings(criteria = publication.criteria)
                HorizontalDivider(color = colors.backgroundDarker)
            }

            // Footer (tags, stats)
            PublicationFooter(publication = publication)
        }
    }
}
