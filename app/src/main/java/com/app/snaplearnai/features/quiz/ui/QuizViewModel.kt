package com.app.snaplearnai.features.quiz.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.snaplearnai.features.camera.domain.model.QuizQuestion
import com.app.snaplearnai.features.quiz.ui.mapper.asUiModel
import com.app.snaplearnai.features.quiz.ui.model.QuizUiState
import com.app.snaplearnai.features.quiz.ui.nav.QuizArguments
import com.app.snaplearnai.shared.ui.BaseViewmodel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val savedStateHandler: SavedStateHandle
) : BaseViewmodel<QuizUiState>(initialState = QuizUiState(isLoading = true)) {

    init {
        viewModelScope.launch {
            val questionJson = savedStateHandler.get<String>(QuizArguments.QUIZ_ARGS)

            val questionList = questionJson?.let {
                Json.decodeFromString<List<QuizQuestion>>(it)
                    .map { question -> question.asUiModel() }
            } ?: emptyList()

            setState { state ->
                state.copy(
                    isLoading = false,
                    allQuestions = questionList
                )
            }
        }
    }

    fun onAnswerSelected(isCorrect: Boolean) {
        if (isCorrect) {
            setState { state ->
                state.copy(correctAnswers = state.correctAnswers + 1)
            }
        }
    }

    fun onNextQuestion() {
        setState { state ->
            state.copy(currentQuestionIndex = state.currentQuestionIndex + 1)
        }
    }
}