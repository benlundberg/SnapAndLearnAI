package com.app.snaplearnai.features.bookmarks.domain.usecase

import com.app.snaplearnai.features.bookmarks.domain.BookmarkRepository
import javax.inject.Inject

class InsertBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(text: String, originalText: String) {
        bookmarkRepository.bookmarkItem(text, originalText)
    }
}