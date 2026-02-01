package com.talkfrly.multiplatform.data.uploads

expect suspend fun readBytesFromUri(uri: String): ByteArray
expect fun guessFileNameFromUri(uri: String): String