package com.app.snaplearnai.features.bookmarks.domain.model

data class BookmarkItem(
    val id: Int,
    val title: String?,
    val text: String,
    val originalText: String,
    val timestamp: Long
)