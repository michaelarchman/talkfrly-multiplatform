package com.talkfrly.multiplatform.di

import android.content.Context
import com.talkfrly.multiplatform.data.datastore.initAndroidDataStore
import com.talkfrly.multiplatform.data.preferences.repository.PreferencesRepository
import com.talkfrly.multiplatform.data.preferences.repository.PreferencesRepositoryImpl
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val androidModule: Module
    get() = module {
        single { initAndroidDataStore(get<Context>()) }
        singleOf(::PreferencesRepositoryImpl).bind<PreferencesRepository>()
    }

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
    }