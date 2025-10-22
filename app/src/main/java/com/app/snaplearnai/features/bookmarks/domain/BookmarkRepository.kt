package com.app.snaplearnai.features.bookmarks.domain

import com.app.snaplearnai.features.bookmarks.domain.model.BookmarkItem
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun getAllBookmarks(): List<BookmarkItem>

    suspend fun bookmarkItem(text: String, originalText: String)

    fun getAllBookmarksFlow(): Flow<List<BookmarkItem>>?

    suspend fun deleteBookmark(bookmarkId: Int)
}