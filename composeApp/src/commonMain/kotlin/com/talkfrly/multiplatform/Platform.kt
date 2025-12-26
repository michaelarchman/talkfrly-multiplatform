package com.talkfrly.multiplatform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform