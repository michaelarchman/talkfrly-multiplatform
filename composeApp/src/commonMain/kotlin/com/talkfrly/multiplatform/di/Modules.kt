package com.talkfrly.multiplatform.di

import com.talkfrly.multiplatform.AppViewModel
import com.talkfrly.multiplatform.data.auth.api.AuthApi
import com.talkfrly.multiplatform.data.auth.api.AuthApiImpl
import com.talkfrly.multiplatform.data.auth.repository.AuthRepository
import com.talkfrly.multiplatform.data.auth.repository.AuthRepositoryImpl
import com.talkfrly.multiplatform.data.cache.CoilImageCacheManager
import com.talkfrly.multiplatform.data.cache.ImageCacheManager
import com.talkfrly.multiplatform.data.comments.api.CommentApi
import com.talkfrly.multiplatform.data.comments.api.CommentApiImpl
import com.talkfrly.multiplatform.data.comments.repository.CommentRepository
import com.talkfrly.multiplatform.data.comments.repository.CommentRepositoryImpl
import com.talkfrly.multiplatform.data.core.HttpClientFactory
import com.talkfrly.multiplatform.data.feed.api.FeedApi
import com.talkfrly.multiplatform.data.feed.api.FeedApiImpl
import com.talkfrly.multiplatform.data.feed.repository.FeedRepository
import com.talkfrly.multiplatform.data.feed.repository.FeedRepositoryImpl
import com.talkfrly.multiplatform.data.preferences.repository.PreferencesRepository
import com.talkfrly.multiplatform.data.publications.api.PublicationApi
import com.talkfrly.multiplatform.data.publications.api.PublicationApiImpl
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepository
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepositoryImpl
import com.talkfrly.multiplatform.data.stream.api.StreamApi
import com.talkfrly.multiplatform.data.stream.api.StreamApiImpl
import com.talkfrly.multiplatform.data.stream.repository.StreamRepository
import com.talkfrly.multiplatform.data.stream.repository.StreamRepositoryImpl
import com.talkfrly.multiplatform.data.threads.api.ThreadApi
import com.talkfrly.multiplatform.data.threads.api.ThreadApiImpl
import com.talkfrly.multiplatform.data.threads.repository.ThreadRepository
import com.talkfrly.multiplatform.data.threads.repository.ThreadRepositoryImpl
import com.talkfrly.multiplatform.data.uploads.api.UploadApi
import com.talkfrly.multiplatform.data.uploads.api.UploadApiImpl
import com.talkfrly.multiplatform.data.uploads.repository.UploadRepository
import com.talkfrly.multiplatform.data.uploads.repository.UploadRepositoryImpl
import com.talkfrly.multiplatform.data.user.api.UserApi
import com.talkfrly.multiplatform.data.user.api.UserApiImpl
import com.talkfrly.multiplatform.data.user.repository.UserRepository
import com.talkfrly.multiplatform.data.user.repository.UserRepositoryImpl
import com.talkfrly.multiplatform.data.userPreferences.UserPreferencesApi
import com.talkfrly.multiplatform.data.userPreferences.UserPreferencesApiImpl
import com.talkfrly.multiplatform.data.userPreferences.UserPreferencesRepository
import com.talkfrly.multiplatform.data.userPreferences.UserPreferencesRepositoryImpl
import com.talkfrly.multiplatform.ui.screens.account.AccountViewModel
import com.talkfrly.multiplatform.ui.screens.createpublication.CreatePublicationViewModel
import com.talkfrly.multiplatform.ui.screens.error.ErrorViewModel
import com.talkfrly.multiplatform.ui.screens.forgotpassword.ForgotPasswordViewModel
import com.talkfrly.multiplatform.ui.screens.home.HomeViewModel
import com.talkfrly.multiplatform.ui.screens.home.feed.FeedTabViewModel
import com.talkfrly.multiplatform.ui.screens.login.LoginViewModel
import com.talkfrly.multiplatform.ui.screens.publication.PublicationScreenViewModel
import com.talkfrly.multiplatform.ui.screens.register.RegisterViewModel
import com.talkfrly.multiplatform.ui.screens.resetpassword.ResetPasswordViewModel
import com.talkfrly.multiplatform.ui.screens.stream.StreamViewModel
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
    single<ImageCacheManager> { CoilImageCacheManager() }

    // Data sources / Api
    singleOf(::AuthApiImpl).bind<AuthApi>()
    singleOf(::PublicationApiImpl).bind<PublicationApi>()
    singleOf(::CommentApiImpl).bind<CommentApi>()
    singleOf(::ThreadApiImpl).bind<ThreadApi>()
    singleOf(::UploadApiImpl).bind<UploadApi>()
    singleOf(::UserApiImpl).bind<UserApi>()
    singleOf(::UserPreferencesApiImpl).bind<UserPreferencesApi>()
    singleOf(::FeedApiImpl).bind<FeedApi>()
    singleOf(::StreamApiImpl).bind<StreamApi>()

    // Repositories
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::PublicationRepositoryImpl).bind<PublicationRepository>()
    singleOf(::CommentRepositoryImpl).bind<CommentRepository>()
    singleOf(::ThreadRepositoryImpl).bind<ThreadRepository>()
    singleOf(::UploadRepositoryImpl).bind<UploadRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
    singleOf(::UserPreferencesRepositoryImpl).bind<UserPreferencesRepository>()
    singleOf(::FeedRepositoryImpl).bind<FeedRepository>()
    singleOf(::StreamRepositoryImpl).bind<StreamRepository>()

    // ViewModels
    viewModelOf(::AppViewModel)
    viewModelOf(::SessionViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::VerifyEmailViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::ResetPasswordViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AccountViewModel)
    viewModelOf(::CreatePublicationViewModel)
    viewModelOf(::PublicationScreenViewModel)
    viewModelOf(::ErrorViewModel)
    viewModelOf(::StreamViewModel)
    viewModelOf(::FeedTabViewModel)
}

expect val platformModule: Module
