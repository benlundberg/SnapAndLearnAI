package com.app.snaplearnai.shared.ui.model

import com.app.snaplearnai.shared.ui.component.notification.SnackBarType

sealed class BaseEvent : OneTimeEvent {
    data class ShowMessage(val message: String, val type: SnackBarType) : BaseEvent()
    data class ShowError(val message: String) : BaseEvent()
    data class ShowSuccess(val message: String) : BaseEvent()
}