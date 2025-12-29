package com.talkfrly.multiplatform

import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.content.FileProvider
import com.multiplatform.webview.web.AccompanistWebChromeClient
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileChooserWebChromeClient(
    context: Context,
    private val fileChooserLauncher: (Any, (Any?) -> Unit) -> Unit
) : AccompanistWebChromeClient() {

    private val applicationContext = context.applicationContext
    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    private var cameraPhotoUri: Uri? = null

    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: WebChromeClient.FileChooserParams?
    ): Boolean {
        if (filePathCallback == null || fileChooserParams == null) return false

        // Cancel any pending callback
        this.filePathCallback?.onReceiveValue(null)
        this.filePathCallback = filePathCallback

        val intents = mutableListOf<Intent>()

        // Gallery/File picker intent
        val contentIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = getMimeType(fileChooserParams)
            if (fileChooserParams.mode == WebChromeClient.FileChooserParams.MODE_OPEN_MULTIPLE) {
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
        }
        intents.add(contentIntent)

        // Camera intent (for image/video only)
        if (shouldAllowCamera(fileChooserParams)) {
            createCameraIntent(fileChooserParams)?.let { intents.add(it) }
        }

        val chooserIntent = Intent.createChooser(
            intents.removeAt(0),
            "Select File"
        ).apply {
            if (intents.isNotEmpty()) {
                putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toTypedArray())
            }
        }

        fileChooserLauncher(chooserIntent as Any) { resultData ->
            handleFileChooserResult(resultData as? Intent)
        }

        return true
    }

    private fun createCameraIntent(
        params: WebChromeClient.FileChooserParams
    ): Intent? {
        val acceptTypes = params.acceptTypes

        return when {
            acceptTypes.any { it.contains("image") } -> {
                Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                    val photoFile = createImageFile()
                    cameraPhotoUri = FileProvider.getUriForFile(
                        applicationContext,
                        "${applicationContext.packageName}.fileprovider",
                        photoFile
                    )
                    putExtra(MediaStore.EXTRA_OUTPUT, cameraPhotoUri)
                    addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }
            }
            acceptTypes.any { it.contains("video") } -> {
                Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            }
            else -> null
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
            .format(Date())
        val storageDir = applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            ?: throw IllegalStateException("External files directory not available")
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    private fun handleFileChooserResult(data: Intent?) {
        val results = when {
            data?.data != null -> arrayOf(data.data!!)
            data?.clipData != null -> {
                val clipData = data.clipData!!
                Array(clipData.itemCount) { clipData.getItemAt(it).uri }
            }
            cameraPhotoUri != null -> {
                // Scan the file so it appears in gallery
                MediaScannerConnection.scanFile(
                    applicationContext,
                    arrayOf(cameraPhotoUri.toString()),
                    null,
                    null
                )
                arrayOf(cameraPhotoUri!!)
            }
            else -> null
        }

        filePathCallback?.onReceiveValue(results)
        filePathCallback = null
        cameraPhotoUri = null
    }

    private fun getMimeType(params: WebChromeClient.FileChooserParams): String {
        val acceptTypes = params.acceptTypes
        return when {
            acceptTypes.isEmpty() -> "*/*"
            acceptTypes.size == 1 -> acceptTypes[0]
            else -> "*/*"
        }
    }

    private fun shouldAllowCamera(params: WebChromeClient.FileChooserParams): Boolean {
        val acceptTypes = params.acceptTypes
        return acceptTypes.isEmpty() || acceptTypes.any {
            it.contains("image") || it.contains("video")
        }
    }
}