package com.talkfrly.multiplatform.domain.models

data class User(
    val id: String,
    val email: String,
    val displayName: String,
    val avatarUrl: String,
    val isVerified: Boolean,
    val isAdmin: Boolean,
    val isApproved: Boolean,
)