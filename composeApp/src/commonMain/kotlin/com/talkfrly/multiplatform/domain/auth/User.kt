package com.talkfrly.multiplatform.domain.auth

data class User(
    val id: String,
    val email: String,
    val displayName: String? = null,
    val avatarUrl: String? = null,
    val isVerified: Boolean,
    val isAdmin: Boolean,
    val isApproved: Boolean,
)