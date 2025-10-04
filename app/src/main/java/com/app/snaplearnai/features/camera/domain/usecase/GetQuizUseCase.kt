package com.app.snaplearnai.features.camera.domain.usecase

import com.app.snaplearnai.features.camera.domain.model.QuizQuestion
import com.app.snaplearnai.features.camera.domain.service.AIService
import javax.inject.Inject

class GetQuizUseCase @Inject constructor(
    private val aiService: AIService
) {

    suspend operator fun invoke(text: String): List<QuizQuestion> {
        return aiService.quizify(text)
    }
}