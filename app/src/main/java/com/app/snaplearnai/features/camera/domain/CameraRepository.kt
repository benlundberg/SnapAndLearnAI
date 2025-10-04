package com.app.snaplearnai.features.camera.domain

import com.app.snaplearnai.features.camera.data.model.CameraDependencies
import kotlinx.coroutines.flow.Flow

interface CameraRepository {
    val recognizedTextFlow: Flow<String>
    fun startAnalysis(cameraDependencies: CameraDependencies, onError: (Exception) -> Unit)
    fun stopAnalysis()
}