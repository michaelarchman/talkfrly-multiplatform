package com.talkfrly.multiplatform.di

import org.koin.core.module.Module
import org.koin.dsl.module

val iosModule = module {
    single { initDataStore() }
}

actual val platformModule: Module
    get() = module {
        Darwin.create()
    }