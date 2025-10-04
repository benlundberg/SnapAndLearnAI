package com.app.snaplearnai.features.camera.data.source

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import com.app.snaplearnai.features.camera.data.util.TextAnalyzer
import com.app.snaplearnai.features.camera.data.model.CameraDependencies
import com.app.snaplearnai.features.camera.domain.CameraRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(@ApplicationContext context: Context) :
    CameraRepository {

    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private var imageAnalysis: ImageAnalysis? = null

    private val _recognizedTextFlow = MutableStateFlow("")
    override val recognizedTextFlow: Flow<String> = _recognizedTextFlow

    private val textAnalyzerListener: (String) -> Unit = { text ->
        _recognizedTextFlow.value = text
    }

    override fun startAnalysis(cameraDependencies: CameraDependencies, onError: (Exception) -> Unit) {
        val textAnalyzer = TextAnalyzer(textAnalyzerListener) {
            // On error
            onError.invoke(it)
        }

        imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, textAnalyzer)
            }

        val preview = Preview.Builder().build().also {
            it.surfaceProvider = cameraDependencies.surfaceProvider
        }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraDependencies.cameraProvider.unbindAll()
            cameraDependencies.cameraProvider.bindToLifecycle(
                cameraDependencies.lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalysis
            )
        } catch (e: Exception) {

        }
    }

    override fun stopAnalysis() {
        // 1. Shut down the executor used by the analyzer
        if (!cameraExecutor.isShutdown) {
            cameraExecutor.shutdown()
        }

        // 2. Unbind the ImageAnalysis use case (optional, depending on overall camera state)
        // If you have a reference to the ProcessCameraProvider and ImageAnalysis instance:
        // cameraProvider.unbind(imageAnalysis)

        // 3. Set internal references to null for garbage collection
        imageAnalysis = null
    }
}