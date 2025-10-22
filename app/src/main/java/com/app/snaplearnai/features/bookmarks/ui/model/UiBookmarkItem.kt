package com.app.snaplearnai.features.bookmarks.ui.model

data class UiBookmarkItem(
    val id: Int,
    val text: String,
    val title: String?,
    val originalText: String,
    val date: String
)