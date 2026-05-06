package com.talkfrly.multiplatform.ui.screens.home.streams

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.talkfrly.multiplatform.domain.stream.StreamCategory
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StreamsTab(
    viewModel: StreamsTabViewModel = koinViewModel(),
    onCategoryClick: (StreamCategory) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.onIntent(StreamsTabIntent.GetStreams)
        }
    }

    LazyRow(
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(state.categories, key = { it.id }) { category ->
            StreamCategoryCard(
                category = category,
                onClick = { onCategoryClick(category) },
            )
        }
    }
}

@Composable
private fun StreamCategoryCard(
    category: StreamCategory,
    onClick: () -> Unit,
) {
    val colors = LocalTalkfrlyColors.current

    Card(
        modifier = Modifier
            .width(140.dp)
            .height(90.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colors.backgroundLighter),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            category.coverUrl?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.background.copy(alpha = 0.45f)),
                )
            }

            Text(
                text = category.name,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp),
                color = colors.body,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )
        }
    }
}