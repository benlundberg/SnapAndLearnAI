package com.app.snaplearnai.features.camera.domain.service

import com.app.snaplearnai.features.camera.domain.model.QuizQuestion

interface AIService {
    suspend fun summarize(text: String): String
    suspend fun translate(text: String, targetLanguage: String): String
    suspend fun explain(text: String): String
    suspend fun quizify(text: String): List<QuizQuestion>
}