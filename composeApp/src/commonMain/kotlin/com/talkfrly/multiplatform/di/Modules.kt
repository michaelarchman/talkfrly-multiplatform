package com.talkfrly.multiplatform.di

import com.talkfrly.multiplatform.AppViewModel
import com.talkfrly.multiplatform.data.auth.api.AuthApi
import com.talkfrly.multiplatform.data.auth.api.AuthApiImpl
import com.talkfrly.multiplatform.data.auth.repository.AuthRepository
import com.talkfrly.multiplatform.data.auth.repository.AuthRepositoryImpl
import com.talkfrly.multiplatform.data.comments.api.CommentApi
import com.talkfrly.multiplatform.data.comments.api.CommentApiImpl
import com.talkfrly.multiplatform.data.comments.repository.CommentRepository
import com.talkfrly.multiplatform.data.comments.repository.CommentRepositoryImpl
import com.talkfrly.multiplatform.data.core.HttpClientFactory
import com.talkfrly.multiplatform.data.preferences.repository.PreferencesRepository
import com.talkfrly.multiplatform.data.publications.api.PublicationApi
import com.talkfrly.multiplatform.data.publications.api.PublicationApiImpl
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepository
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepositoryImpl
import com.talkfrly.multiplatform.data.threads.api.ThreadApi
import com.talkfrly.multiplatform.data.threads.api.ThreadApiImpl
import com.talkfrly.multiplatform.data.threads.repository.ThreadRepository
import com.talkfrly.multiplatform.data.threads.repository.ThreadRepositoryImpl
import com.talkfrly.multiplatform.ui.screens.account.AccountViewModel
import com.talkfrly.multiplatform.ui.screens.createpublication.CreatePublicationViewModel
import com.talkfrly.multiplatform.ui.screens.home.HomeViewModel
import com.talkfrly.multiplatform.ui.screens.login.LoginViewModel
import com.talkfrly.multiplatform.ui.screens.publication.PublicationDetailsViewModel
import com.talkfrly.multiplatform.ui.screens.register.RegisterViewModel
import com.talkfrly.multiplatform.ui.screens.verifyemail.VerifyEmailViewModel
import com.talkfrly.multiplatform.ui.session.SessionViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule: Module = module {
    //httpClient and cookies storage
    single { HttpClientFactory.create(get(), get<PreferencesRepository>()) }

    // Data sources / Api
    singleOf(::AuthApiImpl).bind<AuthApi>()
    singleOf(::PublicationApiImpl).bind<PublicationApi>()
    singleOf(::CommentApiImpl).bind<CommentApi>()
    singleOf(::ThreadApiImpl).bind<ThreadApi>()

    // Repositories
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::PublicationRepositoryImpl).bind<PublicationRepository>()
    singleOf(::CommentRepositoryImpl).bind<CommentRepository>()
    singleOf(::ThreadRepositoryImpl).bind<ThreadRepository>()

    // ViewModels
    viewModelOf(::AppViewModel)
    viewModelOf(::SessionViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::VerifyEmailViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AccountViewModel)
    viewModelOf(::CreatePublicationViewModel)
    viewModelOf(::PublicationDetailsViewModel)
}

expect val platformModule: Module