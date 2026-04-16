package com.talkfrly.multiplatform.data.cache

import coil3.ImageLoader

data class ImageCacheStats(
    val memoryCacheSizeBytes: Long?,
    val diskCacheSizeBytes: Long?,
)

interface ImageCacheManager {
    fun getStats(): ImageCacheStats
    fun clear()
}

object GlobalImageLoaderProvider {
    private var imageLoader: ImageLoader? = null

    fun set(imageLoader: ImageLoader) {
        this.imageLoader = imageLoader
    }

    fun getOrNull(): ImageLoader? = imageLoader
}

class CoilImageCacheManager : ImageCacheManager {
    override fun getStats(): ImageCacheStats {
        val imageLoader = GlobalImageLoaderProvider.getOrNull()
        return ImageCacheStats(
            memoryCacheSizeBytes = imageLoader?.memoryCache?.size,
            diskCacheSizeBytes = imageLoader?.diskCache?.size,
        )
    }

    override fun clear() {
        val imageLoader = GlobalImageLoaderProvider.getOrNull() ?: return
        imageLoader.memoryCache?.clear()
        imageLoader.diskCache?.clear()
    }
}
