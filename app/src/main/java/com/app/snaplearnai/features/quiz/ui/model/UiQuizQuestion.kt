package com.app.snaplearnai.features.quiz.ui.model

data class UiQuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)