package com.talkfrly.multiplatform.data.user

import com.talkfrly.multiplatform.domain.user.User

fun UserDto.toDomain(): User = User(
        id = userId,
        email = email,
        displayName = displayName,
        isAdmin = isAdmin,
//        avatarUrl = avatarUrl,
        isVerified = true,
//        isApproved = isApproved,
    )