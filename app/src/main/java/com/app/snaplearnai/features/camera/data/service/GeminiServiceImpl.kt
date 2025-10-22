package com.app.snaplearnai.features.camera.data.service

import com.app.snaplearnai.BuildConfig
import com.app.snaplearnai.features.camera.domain.service.GeminiService
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import javax.inject.Inject

class GeminiServiceImpl @Inject constructor() : GeminiService {

    companion object {
        private const val MODEL_NAME = "gemini-2.5-flash"
        private const val API_KEY = BuildConfig.GEMINI_API_KEY
    }

    private val client: GenerativeModel = initializeGeminiClient()

    private fun initializeGeminiClient(): GenerativeModel {
        val config = generationConfig {
            temperature = 0.9f
        }

        val safetySettings = listOf(
            SafetySetting(
                HarmCategory.HARASSMENT,
                BlockThreshold.MEDIUM_AND_ABOVE
            ),
            SafetySetting(
                HarmCategory.HATE_SPEECH,
                BlockThreshold.MEDIUM_AND_ABOVE
            ),
            SafetySetting(
                HarmCategory.SEXUALLY_EXPLICIT,
                BlockThreshold.MEDIUM_AND_ABOVE
            ),
            SafetySetting(
                HarmCategory.DANGEROUS_CONTENT,
                BlockThreshold.MEDIUM_AND_ABOVE
            )
        )

        return GenerativeModel(
            modelName = MODEL_NAME,
            apiKey = API_KEY,
            generationConfig = config,
            safetySettings = safetySettings
        )
    }

    override suspend fun generateText(prompt: String): String? {
        val response = client.generateContent(prompt)
        return response.text
    }
}