package com.app.snaplearnai.features.camera.ui.component

import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraPreview(
    onPreviewReady: (LifecycleOwner, ProcessCameraProvider, Preview.SurfaceProvider) -> Unit,
    modifier: Modifier = Modifier
) {
    if (LocalInspectionMode.current) {
        // Do not show camera preview in preview mode
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

        }
        return
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember { PreviewView(context) }

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        factory = { previewView },
        modifier = modifier,
        update = { view ->
            cameraProviderFuture.addListener(
                {
                    val cameraProvider = cameraProviderFuture.get()
                    onPreviewReady(lifecycleOwner, cameraProvider, view.surfaceProvider)
                },
                ContextCompat.getMainExecutor(context),
            )
        }
    )
}