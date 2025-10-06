package com.app.snaplearnai.shared.ui.error

import android.util.Log
import com.app.snaplearnai.R
import com.app.snaplearnai.shared.ui.model.BaseEvent
import com.app.snaplearnai.shared.ui.model.OneTimeEvent
import com.app.snaplearnai.shared.util.LocalizedResources.localized


abstract class BaseUiExceptionHandler {
    fun handleException(e: Exception) : OneTimeEvent {
        trackException(e)
        return evaluateException(e) ?: BaseEvent.ShowError(R.string.unexpected_error.localized())
    }

    protected abstract fun evaluateException(e: Exception) : OneTimeEvent?

    protected open fun trackException(e: Exception) {
        // TODO: Log exception to analytics

        Log.d(e.cause.toString(), e.message.toString())
    }
}