package com.app.snaplearnai.features.camera.ui.model

import com.app.snaplearnai.shared.ui.model.OneTimeEvent

sealed class CameraEvent : OneTimeEvent {
    data class AnswerEvent(val answer: String) : CameraEvent()
    data class QuizCompleteEvent(val questionJson: String) : CameraEvent()
}