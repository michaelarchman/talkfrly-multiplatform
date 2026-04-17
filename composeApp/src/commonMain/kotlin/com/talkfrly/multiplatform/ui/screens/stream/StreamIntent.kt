package com.talkfrly.multiplatform.ui.screens.stream

sealed class StreamIntent {
    data object LoadStream : StreamIntent()
    data object NavigateBack : StreamIntent()
    data class SetComment(val comment: String) : StreamIntent()
}
