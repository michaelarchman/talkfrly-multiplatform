package com.talkfrly.multiplatform.ui.nav

import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data object RegisterRoute

@Serializable
data object VerifyEmailRoute

@Serializable
data object SplashRoute

@Serializable
data object HomeRoute

@Serializable
data object AccountRoute


@Serializable
data class PublicationRoute(
    val publicationId: String
)

@Serializable
data object NewPublicationRoute

@Serializable
data object ForgotPasswordRoute

@Serializable
data class ResetPasswordRoute(val email: String)

@Serializable
data object ErrorRoute

@Serializable
data class StreamRoute(
    val streamId: String
)