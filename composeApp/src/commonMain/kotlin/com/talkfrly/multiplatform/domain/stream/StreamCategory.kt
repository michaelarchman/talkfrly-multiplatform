package com.talkfrly.multiplatform.domain.stream

data class StreamCategory(
    val id: String,
    val name: String,
    val slug: String,
    val coverUrl: String? = null,
)

data class StreamCategoryList(
    val items: List<StreamCategory>,
    val totalCount: Int,
    val page: Int,
    val limit: Int,
)