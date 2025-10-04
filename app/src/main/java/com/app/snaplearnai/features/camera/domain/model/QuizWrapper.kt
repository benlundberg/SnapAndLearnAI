package com.app.snaplearnai.features.camera.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class QuizWrapper(
    val quiz: List<QuizQuestion>
)