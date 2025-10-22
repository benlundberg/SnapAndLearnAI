package com.app.snaplearnai.features.camera.ui.component

import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.CropFree
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.app.snaplearnai.features.camera.data.model.CameraDependencies
import com.app.snaplearnai.shared.ui.theme.AppTheme

@Composable
fun CameraPreview(
    onPreviewReady: (CameraDependencies) -> Unit,
    modifier: Modifier = Modifier,
    isRecognized: Boolean
) {
    Surface(
        color = AppTheme.color.gray950,
        modifier = modifier.fillMaxSize()
    ) {
        if (!LocalInspectionMode.current) {
            // Do not show camera preview in preview mode
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
                            onPreviewReady(
                                CameraDependencies(
                                    lifecycleOwner = lifecycleOwner,
                                    cameraProvider = cameraProvider,
                                    surfaceProvider = view.surfaceProvider
                                )
                            )
                        },
                        ContextCompat.getMainExecutor(context),
                    )
                }
            )
        }

        val buttonColor by animateColorAsState(
            targetValue = if (isRecognized) AppTheme.color.primary else AppTheme.color.white,
            label = "ButtonColorChange"
        )

        Box(
            modifier = Modifier.background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Sharp.CropFree,
                contentDescription = null,
                tint = buttonColor,
                modifier = Modifier.fillMaxSize(.3f)
            )
        }
    }
}