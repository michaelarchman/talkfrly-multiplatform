package com.talkfrly.multiplatform.domain.util

fun detectYouTubeUrl(text: String): String? {
    val youtubeRegex = Regex(
        """(?:https?://)?(?:www\.)?(?:youtube\.com/watch\?v=|youtu\.be/)([\w-]{11})""",
        RegexOption.IGNORE_CASE
    )
    return youtubeRegex.find(text)?.groupValues?.getOrNull(1)
}

fun extractVideoId(url: String): String? {
    val patterns = listOf(
        Regex("""youtube\.com/watch\?v=([\w-]{11})"""),
        Regex("""youtu\.be/([\w-]{11})"""),
        Regex("""youtube\.com/embed/([\w-]{11})"""),
    )

    patterns.forEach { pattern ->
        pattern.find(url)?.groupValues?.getOrNull(1)?.let {
            return it
        }
    }

    return null
}

fun getYouTubeThumbnail(videoId: String): String {
    return "https://img.youtube.com/vi/$videoId/hqdefault.jpg"
}

fun getYouTubeEmbedUrl(videoId: String): String {
    return "https://www.youtube.com/embed/$videoId"
}
