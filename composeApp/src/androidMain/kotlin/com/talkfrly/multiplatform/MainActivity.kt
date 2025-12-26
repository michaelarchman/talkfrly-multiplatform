package com.talkfrly.multiplatform

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

//        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
//        val isDarkMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES
//
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//
//        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
//
//        if (isDarkMode) {
//            // Dark theme - białe ikony
//            insetsController?.isAppearanceLightStatusBars = false
//            insetsController?.isAppearanceLightNavigationBars = false
//        } else {
//            // Light theme - ciemne ikony
//            insetsController?.isAppearanceLightStatusBars = true
//            insetsController?.isAppearanceLightNavigationBars = true
//        }

        setContent {
            App()
        }
    }
}