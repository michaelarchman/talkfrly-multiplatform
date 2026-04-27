package com.talkfrly.multiplatform.domain.simpleThread

data class SimpleThread(
    val id: String,
    val name: String,
    val slug: String,
    val description: String? = null,
    val creatorId: String,
    val memberCount: Int,
//    val dailyPostCount: Int,
//    val dailyVisitors: Int,
//    val resolvedCount: Int,
    val isMember: Boolean? = null,
    val role: String? = null,
    val createdAt: String,
    val updatedAt: String,
)