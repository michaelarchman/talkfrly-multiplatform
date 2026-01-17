package com.talkfrly.multiplatform

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private lateinit var fileChooserLauncher: androidx.activity.result.ActivityResultLauncher<Intent>
    private var pendingFileCallback: ((Intent?) -> Unit)? = null

    private lateinit var permissionLauncher: androidx.activity.result.ActivityResultLauncher<Array<String>>
    private var pendingPermissionCallback: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Enable WebView debugging
        Log.d(TAG, "Enabling WebView debugging...")
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true)
                Log.d(TAG, "WebView debugging enabled successfully")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error enabling WebView debugging", e)
        }

        // Register file chooser launcher
        fileChooserLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val data = if (result.resultCode == Activity.RESULT_OK) {
                result.data
            } else {
                null
            }
            pendingFileCallback?.invoke(data)
            pendingFileCallback = null
        }

        // Register permission launcher
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions.values.all { it }) {
                pendingPermissionCallback?.invoke()
            }
            pendingPermissionCallback = null
        }

        setContent {
            App(
//                fileChooserLauncher = { intent, callback ->
//                    launchFileChooser(intent as Intent) { result ->
//                        callback(result as Any?)
//                    }
//                }
            )
        }
    }

    private fun launchFileChooser(
        intent: Intent,
        callback: (Intent?) -> Unit
    ) {
        val permissions = getRequiredPermissions()
        if (hasPermissions(permissions)) {
            pendingFileCallback = callback
            fileChooserLauncher.launch(intent)
        } else {
            pendingPermissionCallback = {
                pendingFileCallback = callback
                fileChooserLauncher.launch(intent)
            }
            permissionLauncher.launch(permissions)
        }
    }

    private fun getRequiredPermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
            )
        } else {
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun hasPermissions(permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}