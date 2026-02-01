package com.talkfrly.multiplatform.data.uploads

actual suspend fun readBytesFromUri(uri: String): ByteArray {
    val nsUrl = NSURL(string = uri) ?: return ByteArray(0)
    val path = nsUrl.path ?: return ByteArray(0)
    val data = NSFileManager.defaultManager.contentsAtPath(path) ?: return ByteArray(0)
    return data.toByteArray()
}

actual fun guessFileNameFromUri(uri: String): String {
    val nsUrl = NSURL(string = uri)
    val name = nsUrl?.lastPathComponent
    return if (name.isNullOrBlank()) "image.jpg" else name
}

@OptIn(ExperimentalForeignApi::class)
private fun platform.Foundation.NSData.toByteArray(): ByteArray {
    val size = length.toInt()
    if (size == 0) return ByteArray(0)
    val bytes = bytes ?: return ByteArray(0)
    return memScoped {
        val buffer = bytes.reinterpret<ByteVar>()
        buffer.readBytes(size)
    }
}