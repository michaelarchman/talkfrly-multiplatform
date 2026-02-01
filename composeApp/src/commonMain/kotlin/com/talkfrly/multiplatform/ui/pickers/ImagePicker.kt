package com.talkfrly.multiplatform.ui.pickers

import androidx.compose.runtime.Composable

data class ImagePickerResult(
    val uriPaths: List<String>
)

interface ImagePickerController {
    fun openCamera()
    fun openGallery()
    fun onResult(block: (ImagePickerResult) -> Unit)
}

@Composable
expect fun rememberImagePickerController(): ImagePickerController