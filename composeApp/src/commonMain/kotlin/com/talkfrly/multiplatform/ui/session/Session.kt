package com.talkfrly.multiplatform.ui.session

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Session(
    viewModel: SessionViewModel = koinViewModel<SessionViewModel>()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state) { }
}