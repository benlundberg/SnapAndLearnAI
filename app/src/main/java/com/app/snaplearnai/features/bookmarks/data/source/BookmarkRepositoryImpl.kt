package com.app.snaplearnai.features.bookmarks.data.source

import com.app.snaplearnai.features.bookmarks.data.local.dao.BookmarkDao
import com.app.snaplearnai.features.bookmarks.data.local.entities.BookmarkEntity
import com.app.snaplearnai.features.bookmarks.data.local.mapper.asDomainModel
import com.app.snaplearnai.features.bookmarks.domain.BookmarkRepository
import com.app.snaplearnai.features.bookmarks.domain.model.BookmarkItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {
    override suspend fun getAllBookmarks(): List<BookmarkItem> {
        return bookmarkDao.getAllBookmarks().map { it.asDomainModel() }
    }

    override suspend fun bookmarkItem(text: String, originalText: String) {
        bookmarkDao.insertBookmark(
            BookmarkEntity(
                id = 0,
                title = null,
                text = text,
                originalText = originalText,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override fun getAllBookmarksFlow(): Flow<List<BookmarkItem>>? {
        return bookmarkDao.getAllBookmarksFlow()?.map { list -> list.map { it.asDomainModel() } }
    }

    override suspend fun deleteBookmark(bookmarkId: Int) {
        bookmarkDao.deleteBookmark(bookmarkId)
    }
}