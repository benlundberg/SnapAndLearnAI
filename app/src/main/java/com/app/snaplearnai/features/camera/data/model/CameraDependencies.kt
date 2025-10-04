package com.app.snaplearnai.features.camera.data.model

import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner

data class CameraDependencies(
    val lifecycleOwner: LifecycleOwner,
    val cameraProvider: ProcessCameraProvider,
    val surfaceProvider: Preview.SurfaceProvider
)