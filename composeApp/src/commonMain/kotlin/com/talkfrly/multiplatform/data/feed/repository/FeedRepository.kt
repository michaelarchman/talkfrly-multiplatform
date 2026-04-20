package com.talkfrly.multiplatform.data.feed.repository

import com.talkfrly.multiplatform.data.feed.api.FeedApi
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.feed.Feed
import com.talkfrly.multiplatform.domain.feed.FeedItem
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

interface FeedRepository {
    suspend fun getFeed(page: Int, limit: Int): DataResult<Feed, DataError.Remote>
    suspend fun getPopularFeed(page: Int, limit: Int): DataResult<Feed, DataError.Remote>
}

class FeedRepositoryImpl(
    @Suppress("UNUSED_PARAMETER")
    private val api: FeedApi,
) : FeedRepository {
    override suspend fun getFeed(page: Int, limit: Int): DataResult<Feed, DataError.Remote> {
        return DataResult.ResultSuccess(
            Feed(
                feed = mockFeedItems.take(limit.coerceAtLeast(0)),
                page = page,
                limit = limit,
            )
        )
    }

    override suspend fun getPopularFeed(page: Int, limit: Int): DataResult<Feed, DataError.Remote> {
        return DataResult.ResultSuccess(
            Feed(
                feed = mockFeedItems
                    .sortedByDescending { it.voteScore }
                    .take(limit.coerceAtLeast(0)),
                page = page,
                limit = limit,
            )
        )
    }
}

private val mockFeedItems = listOf(
    FeedItem(
        id = "feed-1",
        domain = buildJsonObject {
            put("display_name", "kajetan_dev")
            put("username", "kajetan_dev")
        },
        threadId = "android",
        threadSlug = "android",
        threadName = "Android Dev",
        publicationType = "discussion",
        articleCategory = "Jetpack Compose",
        content = "Przerzuciłem ekran główny na Compose Multiplatform i największa różnica względem starego RecyclerView to to, że całość jest dużo prostsza do czytania. Teraz testuję feed na LazyColumn i układ kart.",
        isAnonymous = false,
        isPrivate = false,
        threadMembersOnly = false,
        isThreadMember = true,
        imageUrls = listOf("https://images.unsplash.com/photo-1515879218367-8466d910aaa4?auto=format&fit=crop&w=1200&q=80"),
        tags = listOf("compose", "kmp", "android"),
        languages = listOf("pl", "en"),
        avatarUrl = null,
        commentCount = 18,
        voteScore = 124,
        likedByUser = true,
        views = 1380,
        createdAt = "2026-04-15T12:00:00Z",
        updatedAt = "2026-04-15T12:00:00Z",
    ),
    FeedItem(
        id = "feed-2",
        domain = buildJsonObject {
            put("display_name", "AnonymousFox")
        },
        threadId = "backend",
        threadSlug = "backend",
        threadName = "Backend",
        publicationType = "question",
        articleCategory = "API",
        content = "Jak najlepiej ujednolicić request DTO dla create i update, jeśli backend ma identyczny shape body? Myślę nad jedną neutralną nazwą zamiast osobnych modeli.",
        isAnonymous = true,
        isPrivate = false,
        threadMembersOnly = false,
        isThreadMember = true,
        tags = listOf("dto", "api", "ktor"),
        languages = listOf("pl"),
        avatarUrl = null,
        commentCount = 7,
        voteScore = 52,
        likedByUser = false,
        views = 412,
        createdAt = "2026-04-15T10:45:00Z",
        updatedAt = "2026-04-15T10:45:00Z",
    ),
    FeedItem(
        id = "feed-3",
        domain = buildJsonObject {
            put("display_name", "monika_ui")
        },
        threadId = "design",
        threadSlug = "design",
        threadName = "Design",
        publicationType = "showcase",
        articleCategory = "UI",
        content = "Próbuję uzyskać kartę feedu bardziej w stylu Reddita, ale nadal spójną z jasną paletą Talkfrly. Boczne statystyki i wyraźny header robią sporą różnicę.",
        isAnonymous = false,
        isPrivate = false,
        threadMembersOnly = false,
        isThreadMember = true,
        imageUrls = listOf("https://images.unsplash.com/photo-1498050108023-c5249f4df085?auto=format&fit=crop&w=1200&q=80"),
        tags = listOf("ui", "reddit-style"),
        languages = listOf("en"),
        avatarUrl = null,
        commentCount = 31,
        voteScore = 209,
        likedByUser = true,
        views = 2894,
        createdAt = "2026-04-14T21:10:00Z",
        updatedAt = "2026-04-15T08:30:00Z",
    ),
    FeedItem(
        id = "feed-4",
        domain = buildJsonObject {
            put("display_name", "team_private")
        },
        threadId = "internal",
        threadSlug = "internal",
        threadName = "Internal",
        publicationType = "note",
        articleCategory = "Private",
        content = "To jest test posta prywatnego tylko dla członków threadu. Dzięki temu można sprawdzić badge private oraz members-only flow bez podpinania backendu.",
        isAnonymous = false,
        isPrivate = true,
        threadMembersOnly = true,
        isThreadMember = true,
        tags = listOf("private", "team"),
        languages = listOf("pl"),
        avatarUrl = null,
        commentCount = 3,
        voteScore = 16,
        likedByUser = false,
        views = 95,
        createdAt = "2026-04-14T18:20:00Z",
        updatedAt = "2026-04-14T18:20:00Z",
    ),
    FeedItem(
        id = "feed-5",
        domain = buildJsonObject {
            put("display_name", "video_streamer")
        },
        channelId = "streams",
        threadId = "video",
        threadSlug = "video",
        threadName = "Video",
        publicationType = "media",
        articleCategory = "Livestream",
        content = "Dorzuciłem testowy wpis z video fields, żeby sprawdzić jak feed zachowa się dla postów bez obrazka, ale z materiałem wideo i większym ruchem.",
        isAnonymous = false,
        isPrivate = false,
        threadMembersOnly = false,
        isThreadMember = true,
        tags = listOf("video", "stream"),
        languages = listOf("en", "pl"),
        avatarUrl = null,
        videoId = "yt-demo-123",
        videoUrl = "https://example.com/video/yt-demo-123",
        videoThumbnail = "https://images.unsplash.com/photo-1574717024453-3540561f0002?auto=format&fit=crop&w=1200&q=80",
        videoEmbedUrl = "https://example.com/embed/yt-demo-123",
        videoStreamUid = "stream-demo-123",
        commentCount = 64,
        voteScore = 341,
        likedByUser = true,
        views = 6402,
        createdAt = "2026-04-13T14:05:00Z",
        updatedAt = "2026-04-14T09:15:00Z",
    ),
)
