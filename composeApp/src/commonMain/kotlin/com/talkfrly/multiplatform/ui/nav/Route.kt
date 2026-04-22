package com.talkfrly.multiplatform.ui.nav

import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data object RegisterRoute

@Serializable
data class VerifyEmailRoute(
    val email: String
)

@Serializable
data object SplashRoute

@Serializable
data object HomeRoute

@Serializable
data object AccountRoute


@Serializable
data class PublicationDetailsRoute(
    val publicationId: String
)

@Serializable
data object NewPublicationRoute

@Serializable
data object ErrorRoute

@Serializable
data class StreamRoute(
    val streamId: String
)