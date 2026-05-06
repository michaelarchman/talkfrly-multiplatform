package com.talkfrly.multiplatform.domain.publication

enum class PublicationType(val apiValue: String, val displayName: String) {
    GENERAL("general", "General"),
    ARTICLE("article", "Article"),
    REVIEW("review", "Review"),
    RANKING("ranking", "Ranking");

    companion object {
        fun fromApiValue(value: String?): PublicationType? =
            entries.firstOrNull { it.apiValue == value }
    }
}