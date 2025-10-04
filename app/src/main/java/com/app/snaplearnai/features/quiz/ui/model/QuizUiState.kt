package com.app.snaplearnai.features.quiz.ui.model

data class QuizUiState(
    val isLoading: Boolean = false,
    val allQuestions: List<UiQuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val correctAnswers: Int = 0
) {
    val currentQuestion: UiQuizQuestion?
        get() = allQuestions.getOrNull(currentQuestionIndex)

    val isLastQuestion: Boolean
        get() = currentQuestionIndex == allQuestions.size - 1
}