package com.talkfrly.multiplatform.di

import com.talkfrly.multiplatform.data.datastore.initDataStore
import com.talkfrly.multiplatform.data.datastore.iosDataStorePath
import com.talkfrly.multiplatform.data.repository.preferences.PreferencesRepository
import com.talkfrly.multiplatform.data.repository.preferences.PreferencesRepositoryImpl
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import io.ktor.client.engine.darwin.Darwin

val iosModule: Module
    get() = module {
    single<() -> String> { ::iosDataStorePath }
    single { initDataStore(get()) }
    singleOf(::PreferencesRepositoryImpl).bind<PreferencesRepository>()
}

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { Darwin.create() }
    }
