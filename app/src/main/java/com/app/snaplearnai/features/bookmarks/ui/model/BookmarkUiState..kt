package com.app.snaplearnai.features.bookmarks.ui.model

import com.app.snaplearnai.shared.ui.model.ViewState

data class BookmarkUiState(
    val isLoading: Boolean = false,
    val bookmarks: List<UiBookmarkItem> = emptyList()
) : ViewState