package com.talkfrly.multiplatform.data.article.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponseDto(
    @SerialName("id") val id: String,
    @SerialName("publication_id") val publicationId: String,
    @SerialName("category") val category: String,
    @SerialName("source") val source: String? = null,
    @SerialName("author_name") val authorName: String? = null,
    @SerialName("bibliography") val bibliography: List<String>? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)

@Serializable
data class ArticleListResponseDto(
    @SerialName("articles") val articles: List<ArticleResponseDto>,
    @SerialName("total_count") val totalCount: Int,
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
)

@Serializable
data class ArticleCategoriesResponseDto(
    @SerialName("categories") val categories: List<String>,
)

@Serializable
data class CreateArticleRequestDto(
    @SerialName("category") val category: String,
    @SerialName("source") val source: String? = null,
    @SerialName("author_name") val authorName: String? = null,
    @SerialName("bibliography") val bibliography: List<String>? = null,
)

@Serializable
data class UpdateArticleRequestDto(
    @SerialName("category") val category: String,
    @SerialName("source") val source: String? = null,
    @SerialName("author_name") val authorName: String? = null,
    @SerialName("bibliography") val bibliography: List<String>? = null,
)