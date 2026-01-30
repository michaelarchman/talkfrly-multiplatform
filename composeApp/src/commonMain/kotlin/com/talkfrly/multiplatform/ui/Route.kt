package com.talkfrly.multiplatform.ui

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    val id: String
    val title: String

    @Serializable data object Login: Route {
        override val id = "LOGIN"
        override val title = "Sign in"
    }

    @Serializable data object Register: Route {
        override val id = "REGISTER"
        override val title = "Sign up"
    }

    @Serializable data object VerifyEmail: Route {
        override val id = "VERIFY_EMAIL"
        override val title = "Verify your account"
    }

    @Serializable data object Splash: Route {
        override val id = "SPLASH"
        override val title = "Loading"
    }

    @Serializable data object Home: Route {
        override val id = "HOME"
        override val title = "Home"
    }

    @Serializable data object Account: Route {
        override val id = "ACCOUNT"
        override val title = "Account"
    }

    @Serializable data class PublicationDetails(val publicationId: String): Route {
        override val id = "PUBLICATION_DETAILS"
        override val title = "Publication"
    }
}
