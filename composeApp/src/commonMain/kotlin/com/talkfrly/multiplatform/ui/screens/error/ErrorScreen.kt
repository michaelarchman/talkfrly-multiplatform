package com.talkfrly.multiplatform.ui.screens.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.domain.core.DataError
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources._404
import talkfrly_multiplatform.composeapp.generated.resources._500
import talkfrly_multiplatform.composeapp.generated.resources._503
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left

@Composable
fun ErrorScreenRoot(
    viewModel: ErrorViewModel = koinViewModel(),
    navController: NavController,
    ) {
    val state by viewModel.state.collectAsState()
    ErrorScreen(
        state = state,
        onGoBack = { navController.popBackStack() }
    )

}

@Composable
fun ErrorScreen(
    state: ErrorState,
    onGoBack: () -> Unit
){

    Box(modifier = Modifier.fillMaxSize()){
        IconButton(
            onClick = onGoBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.chevron_left),
                contentDescription = "Back"
            )
        }
        Image(
            painter = painterResource(
                when(state.error?.code){
                    DataError.HttpErrorCode.NOT_FOUND -> Res.drawable._404
                    DataError.HttpErrorCode.REQUEST_TIMEOUT -> TODO()
                    DataError.HttpErrorCode.BAD_REQUEST -> TODO()
                    DataError.HttpErrorCode.TOO_MANY_REQUESTS -> TODO()
                    DataError.HttpErrorCode.NO_INTERNET -> TODO()
                    DataError.HttpErrorCode.UNAUTHORIZED -> TODO()
                    DataError.HttpErrorCode.SERVER_ERROR -> Res.drawable._500
                    DataError.HttpErrorCode.BAD_GATEWAY -> TODO()
                    DataError.HttpErrorCode.SERVICE_UNAVAILABLE -> Res.drawable._503
                    DataError.HttpErrorCode.GATEWAY_TIMEOUT -> TODO()
                    DataError.HttpErrorCode.SERIALIZATION -> TODO()
                    DataError.HttpErrorCode.TEMPORARY_REDIRECT -> TODO()
                    DataError.HttpErrorCode.UNKNOWN -> TODO()
                    null -> Res.drawable._404
                }
            ),
            contentDescription = "error",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
        )
    }

}