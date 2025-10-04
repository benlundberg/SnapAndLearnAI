package com.app.snaplearnai.features.camera.domain.usecase

import com.app.snaplearnai.features.camera.domain.service.AIService
import javax.inject.Inject

class ExplainTextUseCase @Inject constructor(
    private val aiService: AIService
) {
    suspend operator fun invoke(text: String): String? {
        return aiService.explain(text)
    }
}