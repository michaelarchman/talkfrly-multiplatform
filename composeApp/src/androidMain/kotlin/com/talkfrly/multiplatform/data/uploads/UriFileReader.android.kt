package com.talkfrly.multiplatform.data.uploads

import android.net.Uri
import android.provider.OpenableColumns
import com.talkfrly.multiplatform.platform.internal.AndroidAppContext

actual suspend fun readBytesFromUri(uri: String): ByteArray {
    val parsed = Uri.parse(uri)
    val inputStream = AndroidAppContext.context.contentResolver.openInputStream(parsed) ?: return ByteArray(0)
    return inputStream.use { it.readBytes() }
}

actual fun guessFileNameFromUri(uri: String): String {
    val parsed = Uri.parse(uri)
    val resolver = AndroidAppContext.context.contentResolver
    val cursor = resolver.query(parsed, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (nameIndex >= 0 && it.moveToFirst()) {
            val name = it.getString(nameIndex)
            if (!name.isNullOrBlank()) return name
        }
    }
    return parsed.lastPathSegment ?: "image.jpg"
}