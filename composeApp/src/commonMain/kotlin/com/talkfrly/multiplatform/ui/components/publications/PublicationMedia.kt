package com.talkfrly.multiplatform.ui.components.publications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.talkfrly.multiplatform.domain.publication.Publication
import com.talkfrly.multiplatform.domain.util.detectYouTubeUrl
import com.talkfrly.multiplatform.domain.util.getYouTubeThumbnail
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.play_circle

@Composable
fun PublicationMedia(
    publication: Publication,
    viewMode: PublicationViewMode,
    modifier: Modifier = Modifier,
) {
    val colors = LocalTalkfrlyColors.current

    // Detect YouTube video in content
    val youtubeVideoId = detectYouTubeUrl(publication.content)

    // Show YouTube thumbnail if video found
    if (youtubeVideoId != null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    color = colors.backgroundLighter,
                    shape = RoundedCornerShape(8.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = getYouTubeThumbnail(youtubeVideoId)
                ),
                contentDescription = "YouTube video thumbnail",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
            )

            // Play icon overlay
            Surface(
                modifier = Modifier
                    .align(Alignment.Center),
                color = LocalTalkfrlyColors.current.body.copy(alpha = 0.7f),
                shape = RoundedCornerShape(50),
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.play_circle),
                    contentDescription = "Play video",
                    modifier = Modifier.padding(12.dp),
                    tint = colors.background,
                )
            }

            // YouTube badge
            Surface(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
                color = colors.body.copy(alpha = 0.8f),
                shape = RoundedCornerShape(4.dp),
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "YouTube",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.background,
                    )
                }
            }
        }
    } else if (publication.imageUrls.isNotEmpty()) {
        // Show first image
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    color = colors.backgroundLighter,
                    shape = RoundedCornerShape(8.dp),
                ),
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = publication.imageUrls.first()),
                contentDescription = "Publication image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
            )

            // Image count badge if multiple images
            if (publication.imageUrls.size > 1) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
                    color = colors.body.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "+${publication.imageUrls.size - 1}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.background,
                        )
                    }
                }
            }
        }
    }
}
