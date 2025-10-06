package com.app.snaplearnai.features.camera.ui.error

import com.app.snaplearnai.shared.ui.error.BaseUiExceptionHandler
import com.app.snaplearnai.shared.ui.model.OneTimeEvent
import javax.inject.Inject

class CameraUiExceptionHandler @Inject constructor() : BaseUiExceptionHandler() {
    override fun evaluateException(e: Exception): OneTimeEvent? {
        return null
    }
}