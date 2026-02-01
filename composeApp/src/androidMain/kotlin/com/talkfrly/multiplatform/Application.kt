package com.talkfrly.multiplatform

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.talkfrly.multiplatform.di.androidModule
import com.talkfrly.multiplatform.di.initKoin
import com.talkfrly.multiplatform.platform.internal.AndroidAppContext
import org.koin.android.ext.koin.androidContext

class Application : Application() {


    override fun onCreate() {
        super.onCreate()

        AndroidAppContext.init(this)

        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags("pl")
        )

        initKoin(androidModule) {
            androidContext(this@Application)
        }
    }
}
