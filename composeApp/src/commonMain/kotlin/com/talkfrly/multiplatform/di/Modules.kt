package com.talkfrly.multiplatform.di

import com.talkfrly.multiplatform.data.api.AuthApi
import com.talkfrly.multiplatform.data.api.AuthApiImpl
import com.talkfrly.multiplatform.data.core.HttpClientFactory
import com.talkfrly.multiplatform.data.repository.auth.AuthRepository
import com.talkfrly.multiplatform.data.repository.auth.AuthRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule: Module = module {
    //httpClient
    single { HttpClientFactory.create(get()) }

    // Data sources / Api
    singleOf(::AuthApiImpl).bind<AuthApi>()

    // Repositories
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

    // ViewModels

}

expect val platformModule: Module