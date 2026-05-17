package com.talkfrly.multiplatform.data.article.repository

import com.talkfrly.multiplatform.data.article.api.ArticleApi
import com.talkfrly.multiplatform.data.article.mapper.toDomain
import com.talkfrly.multiplatform.data.article.mapper.toDto
import com.talkfrly.multiplatform.domain.article.Article
import com.talkfrly.multiplatform.domain.article.ArticleList
import com.talkfrly.multiplatform.domain.article.CreateArticleRequest
import com.talkfrly.multiplatform.domain.article.UpdateArticleRequest
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map

interface ArticleRepository {
    suspend fun getCategories(): DataResult<List<String>, DataError.Remote>
    suspend fun getArticles(category: String, page: Int, limit: Int): DataResult<ArticleList, DataError.Remote>
    suspend fun getArticleById(id: String): DataResult<Article, DataError.Remote>
    suspend fun getArticleForPublication(publicationId: String): DataResult<Article, DataError.Remote>
    suspend fun createArticle(publicationId: String, request: CreateArticleRequest): DataResult<Article, DataError.Remote>
    suspend fun updateArticle(publicationId: String, request: UpdateArticleRequest): DataResult<Article, DataError.Remote>
}

class ArticleRepositoryImpl(
    private val api: ArticleApi,
) : ArticleRepository {
    override suspend fun getCategories(): DataResult<List<String>, DataError.Remote> {
        return api.getCategories().map { it.categories }
    }

    override suspend fun getArticles(
        category: String,
        page: Int,
        limit: Int,
    ): DataResult<ArticleList, DataError.Remote> {
        return api.getArticles(category, page, limit).map { it.toDomain() }
    }

    override suspend fun getArticleById(id: String): DataResult<Article, DataError.Remote> {
        return api.getArticleById(id).map { it.toDomain() }
    }

    override suspend fun getArticleForPublication(publicationId: String): DataResult<Article, DataError.Remote> {
        return api.getArticleForPublication(publicationId).map { it.toDomain() }
    }

    override suspend fun createArticle(
        publicationId: String,
        request: CreateArticleRequest,
    ): DataResult<Article, DataError.Remote> {
        return api.createArticle(publicationId, request.toDto()).map { it.toDomain() }
    }

    override suspend fun updateArticle(
        publicationId: String,
        request: UpdateArticleRequest,
    ): DataResult<Article, DataError.Remote> {
        return api.updateArticle(publicationId, request.toDto()).map { it.toDomain() }
    }
}