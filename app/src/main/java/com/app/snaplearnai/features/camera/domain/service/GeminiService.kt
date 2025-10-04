package com.app.snaplearnai.features.camera.domain.service

interface GeminiService {
    suspend fun generateText(prompt: String): String?
}