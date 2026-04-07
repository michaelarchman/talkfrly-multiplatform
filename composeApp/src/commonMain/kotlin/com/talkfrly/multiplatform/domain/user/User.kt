package com.talkfrly.multiplatform.domain.user

data class User(
    val id: String,
    val email: String,
    val displayName: String,
    val isAdmin: Boolean,
//    val avatarUrl: String? = null,
    val isVerified: Boolean,
//    val isApproved: Boolean,
)