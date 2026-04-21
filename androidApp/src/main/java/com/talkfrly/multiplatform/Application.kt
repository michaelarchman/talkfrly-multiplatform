package com.talkfrly.multiplatform

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import org.koin.android.ext.koin.androidContext
import com.talkfrly.multiplatform.di.androidModule
import com.talkfrly.multiplatform.di.initKoin

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags("pl")
        )

        initKoin(androidModule) {
            androidContext(this@Application)
        }
    }
}
