package com.app.snaplearnai.features.bookmarks.ui.error

import com.app.snaplearnai.shared.ui.error.BaseUiExceptionHandler
import com.app.snaplearnai.shared.ui.model.OneTimeEvent
import javax.inject.Inject

class BookmarkUiExceptionHandler @Inject constructor() : BaseUiExceptionHandler() {
    override fun evaluateException(e: Exception): OneTimeEvent? {
        return null
    }
}