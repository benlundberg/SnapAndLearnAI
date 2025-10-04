package com.app.snaplearnai.features.quiz.ui.mapper

import com.app.snaplearnai.features.camera.domain.model.QuizQuestion
import com.app.snaplearnai.features.quiz.ui.model.UiQuizQuestion

fun QuizQuestion.asUiModel() = UiQuizQuestion(
    question = question,
    options = options,
    correctAnswerIndex = correctAnswerIndex
)