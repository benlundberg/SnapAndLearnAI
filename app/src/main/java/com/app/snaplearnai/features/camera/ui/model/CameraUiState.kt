package com.app.snaplearnai.features.camera.ui.model

import com.app.snaplearnai.shared.ui.model.ViewState

data class CameraUiState(
    val isLoading: Boolean = false,
    val functionActions: List<UiFunctionActionModel> = emptyList(),
    val isRecognizedCompleted: Boolean = false,
    val recognizedText: String = "",
    val inputText: String = "",
    val isCameraMode: Boolean = true
) : ViewState