package com.app.snaplearnai.features.camera.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)