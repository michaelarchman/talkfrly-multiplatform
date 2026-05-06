package com.talkfrly.multiplatform.ui.screens.home.streams

sealed class StreamsTabIntent {
    data object GetStreams : StreamsTabIntent()
}