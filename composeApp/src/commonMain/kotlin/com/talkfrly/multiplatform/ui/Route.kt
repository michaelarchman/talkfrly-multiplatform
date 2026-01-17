package com.talkfrly.multiplatform.ui

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    val id: String
    val title: String

    @Serializable data object Login : Route {
        override val id = "LOGIN"
        override val title = "Sign in"
    }

    @Serializable data object Register : Route {
        override val id = "REGISTER"
        override val title = "Sign up"
    }
}