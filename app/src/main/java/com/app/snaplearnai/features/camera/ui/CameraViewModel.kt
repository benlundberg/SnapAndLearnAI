package com.app.snaplearnai.features.camera.ui

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.app.snaplearnai.R
import com.app.snaplearnai.features.bookmarks.domain.usecase.DeleteBookmarkUseCase
import com.app.snaplearnai.features.bookmarks.domain.usecase.InsertBookmarkUseCase
import com.app.snaplearnai.features.camera.data.model.CameraDependencies
import com.app.snaplearnai.features.camera.domain.CameraRepository
import com.app.snaplearnai.features.camera.domain.usecase.ExplainTextUseCase
import com.app.snaplearnai.features.camera.domain.usecase.GetQuizUseCase
import com.app.snaplearnai.features.camera.domain.usecase.SummarizeTextUseCase
import com.app.snaplearnai.features.camera.ui.error.CameraUiExceptionHandler
import com.app.snaplearnai.features.camera.ui.model.CameraEvent
import com.app.snaplearnai.features.camera.ui.model.CameraUiState
import com.app.snaplearnai.features.camera.ui.model.FunctionActionType
import com.app.snaplearnai.features.camera.ui.model.UiFunctionActionModel
import com.app.snaplearnai.shared.ui.BaseViewmodel
import com.app.snaplearnai.shared.util.LocalizedResources.localized
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
    private val getQuizUseCase: GetQuizUseCase,
    private val cameraUiExceptionHandler: CameraUiExceptionHandler,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val insertBookmarkUseCase: InsertBookmarkUseCase
) : BaseViewmodel<CameraUiState>(CameraUiState()) {

    init {
        setState { state ->
            state.copy(
                functionActions = listOf(
                    UiFunctionActionModel(
                        label = R.string.gen_summarize.localized(),
                        type = FunctionActionType.SUMMARY,
                        onClick = ::onFunctionAction
                    ),
                    UiFunctionActionModel(
                        label = R.string.gen_explain.localized(),
                        type = FunctionActionType.EXPLAIN,
                        onClick = ::onFunctionAction
                    ),
                    UiFunctionActionModel(
                        label = R.string.gen_quizify.localized(),
                        type = FunctionActionType.QUIZ,
                        onClick = ::onFunctionAction
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
    fun onPreviewViewReady(cameraDependencies: CameraDependencies) {
        // Start the analysis process
        cameraRepository.startAnalysis(cameraDependencies) { exception ->
            sendEvent(cameraUiExceptionHandler.handleException(exception))
        }
    }

    fun onFunctionAction(type: FunctionActionType) {
        setState { state ->
            state.copy(isLoading = true)
        }

        val textToAnalyze = if (uiState.isCameraMode) uiState.recognizedText else uiState.inputText

        viewModelScope.launch {
            try {
                when (type) {
                    FunctionActionType.SUMMARY -> {
                        val res = summarizeTextUseCase(textToAnalyze)
                        sendEvent(CameraEvent.AnswerEvent(res ?: ""))
                    }

                    FunctionActionType.TRANSLATE -> {
                        // TODO: Translate text
                    }

                    FunctionActionType.EXPLAIN -> {
                        val res = explainTextUseCase(textToAnalyze)
                        sendEvent(CameraEvent.AnswerEvent(res ?: ""))
                    }

                    FunctionActionType.QUIZ -> {
                        val questions = getQuizUseCase(textToAnalyze)
                        val questionJson = Uri.encode(Gson().toJson(questions))

                        sendEvent(CameraEvent.QuizCompleteEvent(questionJson ?: ""))
                    }
                }
            } catch (e: Exception) {
                sendEvent(cameraUiExceptionHandler.handleException(e))
            } finally {
                setState { state ->
                    state.copy(isLoading = false)
                }
            }
        }
    }

    fun onInputModeChanged(isCameraMode: Boolean) {
        setState { state ->
            state.copy(isCameraMode = isCameraMode)
        }
    }

    fun onTextChange(text: String) {
        setState { state ->
            state.copy(inputText = text, isRecognizedCompleted = text.isNotEmpty())
        }
    }

    fun onBookmark(isBookmarked: Boolean, text: String) {
        viewModelScope.launch {
            if (isBookmarked) {
                insertBookmarkUseCase.invoke(
                    text = text,
                    originalText = if (uiState.isCameraMode) uiState.recognizedText else uiState.inputText
                )
            } else {
                deleteBookmarkUseCase.invoke(null)
            }
        }
    }

    override fun onCleared() {
        cameraRepository.stopAnalysis()
        super.onCleared()
    }
}