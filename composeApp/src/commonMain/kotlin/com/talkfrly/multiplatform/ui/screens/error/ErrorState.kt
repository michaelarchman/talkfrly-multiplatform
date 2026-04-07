package com.talkfrly.multiplatform.ui.screens.error

import com.talkfrly.multiplatform.domain.core.DataError

data class ErrorState (
    val error: DataError.Remote ?= null
)