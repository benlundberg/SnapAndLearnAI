package com.app.snaplearnai.features.bookmarks.ui

import androidx.lifecycle.viewModelScope
import com.app.snaplearnai.features.bookmarks.domain.BookmarkRepository
import com.app.snaplearnai.features.bookmarks.ui.error.BookmarkUiExceptionHandler
import com.app.snaplearnai.features.bookmarks.ui.mapper.asUiModel
import com.app.snaplearnai.features.bookmarks.ui.model.BookmarkUiState
import com.app.snaplearnai.shared.ui.BaseViewmodel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
    private val bookmarkUiExceptionHandler: BookmarkUiExceptionHandler
) : BaseViewmodel<BookmarkUiState>(BookmarkUiState(isLoading = true)) {

    init {
        observeBookmarks()
    }

    private fun observeBookmarks() {
        bookmarkRepository.getAllBookmarksFlow()?.onEach { bookmarks ->
            setState { state ->
                state.copy(
                    isLoading = false,
                    bookmarks = bookmarks.map { it.asUiModel() }
                )
            }
        }?.launchIn(viewModelScope)
    }

    fun onDelete(id: Int?) {
        id?.let {
            viewModelScope.launch {
                bookmarkRepository.deleteBookmark(bookmarkId = id)
            }
        }
    }
}