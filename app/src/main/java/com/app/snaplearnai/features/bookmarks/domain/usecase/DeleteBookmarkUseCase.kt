package com.app.snaplearnai.features.bookmarks.domain.usecase

import com.app.snaplearnai.features.bookmarks.domain.BookmarkRepository
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {

    suspend operator fun invoke(bookmarkId: Int?) {
        (bookmarkId ?: bookmarkRepository.getAllBookmarks().lastOrNull()?.id)?.let { id ->
            bookmarkRepository.deleteBookmark(id)
        }
    }
}