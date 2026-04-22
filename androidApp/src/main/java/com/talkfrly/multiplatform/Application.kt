package com.talkfrly.multiplatform

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import org.koin.android.ext.koin.androidContext
import com.talkfrly.multiplatform.di.androidModule
import com.talkfrly.multiplatform.di.initKoin
import com.talkfrly.multiplatform.platform.internal.AndroidAppContext

class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidAppContext.init(this)

        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags("en")
        )

        initKoin(androidModule) {
            androidContext(this@Application)
        }
    }
}
