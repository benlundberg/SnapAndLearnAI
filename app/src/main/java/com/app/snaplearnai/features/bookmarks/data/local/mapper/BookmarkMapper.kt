package com.app.snaplearnai.features.bookmarks.data.local.mapper

import com.app.snaplearnai.features.bookmarks.data.local.entities.BookmarkEntity
import com.app.snaplearnai.features.bookmarks.domain.model.BookmarkItem

fun BookmarkEntity.asDomainModel() = BookmarkItem(
    id = id,
    title = title,
    text = text,
    originalText = originalText,
    timestamp = timestamp
)

fun BookmarkItem.asEntity() = BookmarkEntity(
    id = id,
    title = title,
    text = text,
    originalText = originalText,
    timestamp = timestamp
)