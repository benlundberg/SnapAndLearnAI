package com.app.snaplearnai.features.camera.ui

import android.net.Uri
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.app.snaplearnai.features.camera.data.model.CameraDependencies
import com.app.snaplearnai.features.camera.domain.CameraRepository
import com.app.snaplearnai.features.camera.domain.usecase.ExplainTextUseCase
import com.app.snaplearnai.features.camera.domain.usecase.GetQuizUseCase
import com.app.snaplearnai.features.camera.domain.usecase.SummarizeTextUseCase
import com.app.snaplearnai.features.camera.ui.model.CameraEvent
import com.app.snaplearnai.features.camera.ui.model.CameraUiState
import com.app.snaplearnai.features.camera.ui.model.FunctionActionType
import com.app.snaplearnai.features.camera.ui.model.UiFunctionActionModel
import com.app.snaplearnai.shared.ui.BaseViewmodel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val cameraRepository: CameraRepository,
    private val summarizeTextUseCase: SummarizeTextUseCase,
    private val explainTextUseCase: ExplainTextUseCase,
    private val getQuizUseCase: GetQuizUseCase
) : BaseViewmodel<CameraUiState>(CameraUiState()) {

    init {
        setState { state ->
            state.copy(
                functionAction = listOf(
                    UiFunctionActionModel(
                        label = "Summarize",
                        type = FunctionActionType.SUMMARY,
                        onClick = ::onSummarizeText
                    ),
                    UiFunctionActionModel(
                        label = "Translate",
                        type = FunctionActionType.TRANSLATE,
                        onClick = ::onTranslateText
                    ),
                    UiFunctionActionModel(
                        label = "Explain",
                        type = FunctionActionType.EXPLAIN,
                        onClick = ::onExplainText
                    ),
                    UiFunctionActionModel(
                        label = "Quizify",
                        type = FunctionActionType.QUIZ,
                        onClick = ::onQuizifyTextText
                    )
                )
            )
        }

        observeDataFlows()
    }

    private fun observeDataFlows() {
        // When text is recognized, update the UI state
        cameraRepository.recognizedTextFlow.onEach { text ->

            if (!uiState.isLoading) {
                setState { state ->
                    state.copy(recognizedText = text, isRecognizedCompleted = !text.isEmpty())
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Call from the Composable (UI) once the PreviewView is ready. This method
     * will start the text analysis
     */
    fun onPreviewViewReady(
        owner: LifecycleOwner,
        provider: ProcessCameraProvider,
        surface: Preview.SurfaceProvider
    ) {
        // Start the analysis process
        cameraRepository.startAnalysis(
            CameraDependencies(
                lifecycleOwner = owner,
                cameraProvider = provider,
                surfaceProvider = surface
            )
        ) { exception ->
            // TODO: Handle exception
        }
    }

    fun onSummarizeText() {
        setState { state ->
            state.copy(isLoading = true)
        }

        viewModelScope.launch {
            try {
                val result = summarizeTextUseCase(uiState.recognizedText)
                sendEvent(CameraEvent.AnswerEvent(result ?: ""))
            } catch (e: Exception) {
                // TODO: Handle exception
            } finally {
                setState { state ->
                    state.copy(isLoading = false)
                }
            }
        }
    }

    fun onTranslateText() {

    }

    fun onExplainText() {
        setState { state ->
            state.copy(isLoading = true)
        }

        viewModelScope.launch {
            try {
                val result = explainTextUseCase(uiState.recognizedText)
                sendEvent(CameraEvent.AnswerEvent(result ?: ""))
            } catch (e: Exception) {
                1
                // TODO: Handle exception
            } finally {
                setState { state ->
                    state.copy(isLoading = false)
                }
            }
        }
    }

    fun onQuizifyTextText() {
        setState { state ->
            state.copy(isLoading = true)
        }

        viewModelScope.launch {
            try {
                val questions = getQuizUseCase(uiState.recognizedText)
                val questionJson = Uri.encode(Gson().toJson(questions))

                sendEvent(CameraEvent.QuizCompleteEvent(questionJson ?: ""))
            } catch (e: Exception) {
                // TODO: Handle exception
            } finally {
                setState { state ->
                    state.copy(isLoading = false)
                }
            }
        }
    }

    override fun onCleared() {
        cameraRepository.stopAnalysis()
        super.onCleared()
    }
}