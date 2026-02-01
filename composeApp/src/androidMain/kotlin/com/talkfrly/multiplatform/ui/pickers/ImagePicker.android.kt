package com.talkfrly.multiplatform.ui.pickers

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

@Composable
actual fun rememberImagePickerController(): ImagePickerController {
    val context = LocalContext.current

    var onResultCallback by remember { mutableStateOf<(ImagePickerResult) -> Unit>({}) }
    var openCameraAction by remember { mutableStateOf<() -> Unit>({}) }
    var openGalleryAction by remember { mutableStateOf<() -> Unit>({}) }

    var pendingCameraUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            onResultCallback(ImagePickerResult(listOf(uri.toString())))
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            pendingCameraUri?.let { uri ->
                onResultCallback(ImagePickerResult(listOf(uri.toString())))
            }
        }
    }

    openGalleryAction = {
        galleryLauncher.launch(
            PickVisualMediaRequest(
                ActivityResultContracts.PickVisualMedia.ImageOnly
            )
        )
    }

    openCameraAction = {
        val uri = createTempImageUri(context)
        pendingCameraUri = uri
        cameraLauncher.launch(uri)
    }

    return remember {
        object : ImagePickerController {
            override fun openCamera() = openCameraAction()
            override fun openGallery() = openGalleryAction()
            override fun onResult(block: (ImagePickerResult) -> Unit) {
                onResultCallback = block
            }
        }
    }
}

private fun createTempImageUri(context: Context): Uri {
    val tempFile = File.createTempFile(
        "talkfrly_camera_",
        ".jpg",
        context.cacheDir
    )
    val authority = "${context.packageName}.fileprovider"
    return FileProvider.getUriForFile(context, authority, tempFile)
}