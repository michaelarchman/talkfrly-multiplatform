package com.talkfrly.multiplatform.data.mapper

import com.talkfrly.multiplatform.data.dto.UserDto
import com.talkfrly.multiplatform.domain.models.User

fun UserDto.toDomain(): User = User(
    id = id,
    email = email,
    displayName = displayName,
    avatarUrl = avatarUrl,
    isVerified = isVerified,
    isAdmin = isAdmin,
    isApproved = isApproved,
)