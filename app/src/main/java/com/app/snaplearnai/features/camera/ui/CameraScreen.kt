package com.app.snaplearnai.features.camera.ui

import android.Manifest
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.app.snaplearnai.R
import com.app.snaplearnai.features.camera.data.model.CameraDependencies
import com.app.snaplearnai.features.camera.ui.component.BottomSheetText
import com.app.snaplearnai.features.camera.ui.component.CameraPermissionRationale
import com.app.snaplearnai.features.camera.ui.component.CameraPreview
import com.app.snaplearnai.features.camera.ui.component.FunctionActionsView
import com.app.snaplearnai.features.camera.ui.model.CameraEvent
import com.app.snaplearnai.features.camera.ui.model.CameraUiState
import com.app.snaplearnai.features.camera.ui.model.FunctionActionType
import com.app.snaplearnai.features.camera.ui.model.UiFunctionActionModel
import com.app.snaplearnai.shared.ui.component.loading.CustomLoading
import com.app.snaplearnai.shared.ui.theme.AppTheme
import com.app.snaplearnai.shared.ui.theme.lighter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    viewModel: CameraViewModel = hiltViewModel(),
    onQuiz: (String) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetText by remember { mutableStateOf("") }

    // Handle events from view model
    LaunchedEffect(Unit) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                is CameraEvent.AnswerEvent -> {
                    // Show simple text answer result in bottom sheet
                    showBottomSheet = true
                    bottomSheetText = event.answer
                }

                is CameraEvent.QuizCompleteEvent -> {
                    // Will navigate to quiz screen and pass the question json
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

            // Camera is granted, show content
            CameraContent(
                state = state,
                onPreviewReady = viewModel::onPreviewViewReady,
                onInputModeChanged = viewModel::onInputModeChanged,
                onInputTextChanged = viewModel::onTextChange
            )
        }

        cameraPermissionState.status.shouldShowRationale -> {
            CameraPermissionRationale {
                cameraPermissionState.launchPermissionRequest()
            }
        }

        else -> {
            SideEffect {
                cameraPermissionState.launchPermissionRequest()
            }
        }
    }

    // Bottom sheet to show simple text answer
    BottomSheetText(
        text = bottomSheetText,
        showBottomSheet = showBottomSheet,
        onBookmark = viewModel::onBookmark,
        onDismiss = { showBottomSheet = false }
    )
}

@Composable
private fun CameraContent(
    state: CameraUiState,
    onPreviewReady: (CameraDependencies) -> Unit,
    onInputModeChanged: (Boolean) -> Unit,
    onInputTextChanged: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Crossfade(targetState = state.isCameraMode) { isCameraMode ->
            Column {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    if (isCameraMode) {
                        CameraPreview(
                            onPreviewReady = onPreviewReady,
                        )
                    } else {
                        TextInputView(
                            state = state,
                            onInputTextChanged = onInputTextChanged
                        )
                    }

                    FloatingActionButton(
                        onClick = {
                            onInputModeChanged(!isCameraMode)
                        },
                        containerColor = AppTheme.color.secondary.lighter(.8f),
                        modifier = Modifier
                            .padding(AppTheme.spacings.xl)
                    ) {
                        Icon(
                            imageVector = if (isCameraMode) Icons.Default.TextFields else Icons.Default.CameraAlt,
                            contentDescription = null
                        )
                    }
                }

                FunctionActionsView(
                    actions = state.functionActions,
                    isRecognizeComplete = state.isRecognizedCompleted
                )
            }
        }

        if (state.isLoading) {
            CustomLoading()
        }
    }
}

@Composable
fun TextInputView(
    state: CameraUiState,
    onInputTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .imePadding()
            .padding(AppTheme.spacings.m)
    ) {
        TextField(
            value = state.inputText,
            modifier = Modifier.fillMaxSize(),
            onValueChange = { onInputTextChanged(it) },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.input_text_placeholder),
                    style = AppTheme.typography.default
                )
            }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun LightPreview() {
    AppTheme {
        PreviewContent(isCameraMode = true)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DarkPreview() {
    AppTheme(darkTheme = true) {
        PreviewContent(isCameraMode = false)
    }
}

@Composable
private fun PreviewContent(isCameraMode: Boolean) {
    CameraContent(
        state = CameraUiState(
            isLoading = false,
            isCameraMode = isCameraMode,
            functionActions = listOf(
                UiFunctionActionModel(
                    label = "Summary",
                    type = FunctionActionType.SUMMARY,
                    onClick = { }
                ),
                UiFunctionActionModel(
                    label = "Translate",
                    type = FunctionActionType.TRANSLATE,
                    onClick = { }
                ),
                UiFunctionActionModel(
                    label = "Explain",
                    type = FunctionActionType.EXPLAIN,
                    onClick = { }
                ),
                UiFunctionActionModel(
                    label = "Quizify",
                    type = FunctionActionType.QUIZ,
                    onClick = { }
                )
            )
        ),
        onPreviewReady = { },
        onInputModeChanged = { },
        onInputTextChanged = { }
    )
}