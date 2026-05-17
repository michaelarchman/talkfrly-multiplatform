package com.talkfrly.multiplatform.domain.article

data class Article(
    val id: String,
    val publicationId: String,
    val category: String,
    val source: String? = null,
    val authorName: String? = null,
    val bibliography: List<String>? = null,
    val createdAt: String,
    val updatedAt: String,
)

data class ArticleList(
    val articles: List<Article>,
    val totalCount: Int,
    val page: Int,
    val limit: Int,
)

data class CreateArticleRequest(
    val category: String,
    val source: String? = null,
    val authorName: String? = null,
    val bibliography: List<String>? = null,
)

data class UpdateArticleRequest(
    val category: String,
    val source: String? = null,
    val authorName: String? = null,
    val bibliography: List<String>? = null,
)