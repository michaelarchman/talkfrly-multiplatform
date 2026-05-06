package com.talkfrly.multiplatform.data.article.api

import com.talkfrly.multiplatform.data.article.dto.ArticleCategoriesResponseDto
import com.talkfrly.multiplatform.data.article.dto.ArticleListResponseDto
import com.talkfrly.multiplatform.data.article.dto.ArticleResponseDto
import com.talkfrly.multiplatform.data.article.dto.CreateArticleRequestDto
import com.talkfrly.multiplatform.data.article.dto.UpdateArticleRequestDto
import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface ArticleApi {
    suspend fun getCategories(): DataResult<ArticleCategoriesResponseDto, DataError.Remote>
    suspend fun getArticles(category: String, page: Int, limit: Int): DataResult<ArticleListResponseDto, DataError.Remote>
    suspend fun getArticleById(id: String): DataResult<ArticleResponseDto, DataError.Remote>
    suspend fun getArticleForPublication(publicationId: String): DataResult<ArticleResponseDto, DataError.Remote>
    suspend fun createArticle(publicationId: String, request: CreateArticleRequestDto): DataResult<ArticleResponseDto, DataError.Remote>
    suspend fun updateArticle(publicationId: String, request: UpdateArticleRequestDto): DataResult<ArticleResponseDto, DataError.Remote>
}

class ArticleApiImpl(
    private val httpClient: HttpClient,
) : ArticleApi {
    override suspend fun getCategories(): DataResult<ArticleCategoriesResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/articles/categories",
        )
    }

    override suspend fun getArticles(
        category: String,
        page: Int,
        limit: Int,
    ): DataResult<ArticleListResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/articles",
            queryParams = mapOf(
                "category" to category,
                "page" to page,
                "limit" to limit,
            ),
        )
    }

    override suspend fun getArticleById(id: String): DataResult<ArticleResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/articles/$id",
        )
    }

    override suspend fun getArticleForPublication(publicationId: String): DataResult<ArticleResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/article",
        )
    }

    override suspend fun createArticle(
        publicationId: String,
        request: CreateArticleRequestDto,
    ): DataResult<ArticleResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Post,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/article",
            body = request,
        )
    }

    override suspend fun updateArticle(
        publicationId: String,
        request: UpdateArticleRequestDto,
    ): DataResult<ArticleResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Put,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/article",
            body = request,
        )
    }
}