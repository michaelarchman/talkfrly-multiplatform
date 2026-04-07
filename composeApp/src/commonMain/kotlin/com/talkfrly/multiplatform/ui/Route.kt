package com.talkfrly.multiplatform.ui

import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
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

    @Serializable data object Profile: Route {
        override val id = "Profile"
        override val title = "Profile"
    }

    @Serializable data class PublicationDetails(val publicationId: String): Route {
        override val id = "PUBLICATION_DETAILS"
        override val title = "Publication"
    }

    @Serializable data class CreatePublication(
        val threadId: String? = null,
        val threadName: String? = null
    ): Route {
        override val id = "CREATE_PUBLICATION"
        override val title = "Create Publication"
    }
    @Serializable
    data object Error : Route {
        override val id = "ERROR"
        override val title = "Error"
    }

}
