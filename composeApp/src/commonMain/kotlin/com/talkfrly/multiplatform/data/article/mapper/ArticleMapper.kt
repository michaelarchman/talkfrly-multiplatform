package com.talkfrly.multiplatform.data.article.mapper

import com.talkfrly.multiplatform.data.article.dto.ArticleListResponseDto
import com.talkfrly.multiplatform.data.article.dto.ArticleResponseDto
import com.talkfrly.multiplatform.data.article.dto.CreateArticleRequestDto
import com.talkfrly.multiplatform.data.article.dto.UpdateArticleRequestDto
import com.talkfrly.multiplatform.domain.article.Article
import com.talkfrly.multiplatform.domain.article.ArticleList
import com.talkfrly.multiplatform.domain.article.CreateArticleRequest
import com.talkfrly.multiplatform.domain.article.UpdateArticleRequest

fun ArticleResponseDto.toDomain(): Article = Article(
    id = id,
    publicationId = publicationId,
    category = category,
    source = source,
    authorName = authorName,
    bibliography = bibliography,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun ArticleListResponseDto.toDomain(): ArticleList = ArticleList(
    articles = articles.map { it.toDomain() },
    totalCount = totalCount,
    page = page,
    limit = limit,
)

fun CreateArticleRequest.toDto(): CreateArticleRequestDto = CreateArticleRequestDto(
    category = category,
    source = source,
    authorName = authorName,
    bibliography = bibliography,
)

fun UpdateArticleRequest.toDto(): UpdateArticleRequestDto = UpdateArticleRequestDto(
    category = category,
    source = source,
    authorName = authorName,
    bibliography = bibliography,
)