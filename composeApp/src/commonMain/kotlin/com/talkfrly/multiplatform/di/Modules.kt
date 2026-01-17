package com.talkfrly.multiplatform.di

import com.talkfrly.multiplatform.AppViewModel
import com.talkfrly.multiplatform.data.api.AuthApi
import com.talkfrly.multiplatform.data.api.AuthApiImpl
import com.talkfrly.multiplatform.data.core.HttpClientFactory
import com.talkfrly.multiplatform.data.repository.auth.AuthRepository
import com.talkfrly.multiplatform.data.repository.auth.AuthRepositoryImpl
import com.talkfrly.multiplatform.ui.screens.login.LoginViewModel
import com.talkfrly.multiplatform.ui.screens.register.RegisterViewModel
import com.talkfrly.multiplatform.ui.session.SessionViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
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
    viewModelOf(::AppViewModel)
    viewModelOf(::SessionViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)

}

expect val platformModule: Module