package com.app.snaplearnai.features.bookmarks.ui.mapper

import com.app.snaplearnai.features.bookmarks.domain.model.BookmarkItem
import com.app.snaplearnai.features.bookmarks.ui.ktx.convertTimestampToDateTimeString
import com.app.snaplearnai.features.bookmarks.ui.model.UiBookmarkItem

fun BookmarkItem.asUiModel() = UiBookmarkItem(
    id = id,
    text = text,
    title = title,
    originalText = originalText,
    date = timestamp.convertTimestampToDateTimeString()
)