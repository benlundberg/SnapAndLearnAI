package com.app.snaplearnai.features.camera.ui

import android.Manifest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.app.snaplearnai.features.camera.ui.component.CameraPreview
import com.app.snaplearnai.features.camera.ui.component.PermissionRationale
import com.app.snaplearnai.features.camera.ui.component.TextFunctionButton
import com.app.snaplearnai.features.camera.ui.model.CameraEvent
import com.app.snaplearnai.features.camera.ui.model.CameraUiState
import com.app.snaplearnai.features.camera.ui.model.UiFunctionActionModel
import com.app.snaplearnai.shared.ui.component.loading.CustomLoading
import com.app.snaplearnai.shared.ui.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    viewModel: CameraViewModel = hiltViewModel(),
    onQuiz: (String) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                is CameraEvent.AnswerEvent -> {
                    showBottomSheet = true
                    bottomSheetText = event.answer
                }

                is CameraEvent.QuizCompleteEvent -> {
                    onQuiz(event.questionJson)
                }
            }
        }
    }

    // Check camera permission
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    when {
        cameraPermissionState.status.isGranted -> {
            val state by viewModel.uiStateFlow.collectAsState()
            CameraContent(
                state = state,
                onPreviewReady = viewModel::onPreviewViewReady,
            )
        }

        cameraPermissionState.status.shouldShowRationale -> {
            PermissionRationale {
                cameraPermissionState.launchPermissionRequest()
            }
        }

        else -> {
            SideEffect {
                cameraPermissionState.launchPermissionRequest()
            }
        }
    }

    BottomSheetText(
        text = bottomSheetText,
        showBottomSheet = showBottomSheet,
        onDismiss = { showBottomSheet = false }
    )
}

@Composable
private fun CameraContent(
    state: CameraUiState,
    onPreviewReady: (LifecycleOwner, ProcessCameraProvider, androidx.camera.core.Preview.SurfaceProvider) -> Unit
) {
    // Camera preview
    CameraPreview(onPreviewReady = onPreviewReady)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars),
        contentAlignment = Alignment.BottomCenter,
    ) {
        BottomActionButtons(
            actions = state.functionAction,
            isRecognizeComplete = state.isRecognizedCompleted
        )

        if (state.isLoading) {
            CustomLoading()
        }
    }
}

@Composable
private fun BottomActionButtons(
    actions: List<UiFunctionActionModel>,
    isRecognizeComplete: Boolean
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.5f))
            .padding(horizontal = AppTheme.spacings.m, vertical = AppTheme.spacings.l),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacings.s)
    ) {
        var isButtonsTriggered by remember { mutableStateOf(false) }
        val resetButtonsAnimation = { isButtonsTriggered = false }

        LaunchedEffect(isRecognizeComplete) {
            if (isRecognizeComplete) {
                isButtonsTriggered = true
            }
        }

        if (isButtonsTriggered) {
            LaunchedEffect(true) {
                delay(500)
                resetButtonsAnimation()
            }
        }

        actions.forEach { actionModel ->
            TextFunctionButton(
                label = actionModel.label,
                type = actionModel.type,
                isEnabled = isRecognizeComplete,
                isTriggered = isButtonsTriggered,
                onClick = actionModel.onClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetText(
    text: String,
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val scope = rememberCoroutineScope()

    // 4. Show the ModalBottomSheet when the state is true
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                // This is called when the user swipes down or taps the scrim
                onDismiss()
            },
            sheetState = sheetState
        ) {
            // Your custom content goes here (e.g., Column with Text and Button)
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // The Text you want to display
                Text(
                    text = text,
                    style = AppTheme.typography.default
                )
                Spacer(Modifier.height(32.dp))

                // Button to hide the sheet programmatically
                Button(
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            // Only remove it from composition AFTER the hide animation is done
                            if (!sheetState.isVisible) {
                                onDismiss()
                            }
                        }
                    }
                ) {
                    Text("Dismiss")
                }
                Spacer(Modifier.height(16.dp)) // Extra padding at the bottom
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun LightPreview() {
    AppTheme {
        CameraContent(
            state = CameraUiState(),
        ) { _, _, _ ->

        }
    }
}