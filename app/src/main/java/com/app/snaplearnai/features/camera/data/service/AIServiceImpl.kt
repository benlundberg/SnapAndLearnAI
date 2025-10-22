package com.app.snaplearnai.features.camera.data.service

import com.app.snaplearnai.features.camera.domain.model.QuizQuestion
import com.app.snaplearnai.features.camera.domain.model.QuizWrapper
import com.app.snaplearnai.features.camera.domain.service.AIService
import com.app.snaplearnai.features.camera.domain.service.GeminiService
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AIServiceImpl @Inject constructor(
    private val geminiService: GeminiService
) : AIService {

    override suspend fun summarize(text: String): String {
        val prompt = "Please summarize the following text concisely in same language as the text: $text"
        return geminiService.generateText(prompt) ?: ""
    }

    override suspend fun translate(
        text: String,
        targetLanguage: String
    ): String {
        val prompt = "Translate the following text into $targetLanguage: $text"
        return geminiService.generateText(prompt) ?: ""
    }

    override suspend fun explain(text: String): String {
        val prompt = "Please explain the following text in short sentences in same language as the text: $text"
        return geminiService.generateText(prompt) ?: ""
    }

    override suspend fun quizify(text: String): List<QuizQuestion> {
        val jsonTemplate = """
            {
              "quiz": [
                {
                  "question": "string",
                  "options": ["string", "string", "string", "string"],
                  "correctAnswerIndex": 0
                }
              ]
            }
        """.trimIndent()
        val prompt = """
            You are a quiz generation API. 
            Your only task is to analyze the provided text and generate a quiz with around 5 multiple-choice questions. 
            
            You must strictly return a single JSON object. 
            DO NOT include any text, greetings, explanations, or MARKDOWN CODE BLOCK DELIMITERS (like ```json or ```) outside of the JSON.
            
            Please return the JSON-data in the detected language
            
            Structure:
            $jsonTemplate
            Text to analyze:
            $text
            """.trimIndent()

        val response = geminiService.generateText(prompt) ?: ""

        // Parse the generated text to create quiz questions
        try {
            val jsonParser = Json {
                ignoreUnknownKeys = true
                isLenient = true
            }
            val quizWrapper = jsonParser.decodeFromString<QuizWrapper>(response)
            return quizWrapper.quiz
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return emptyList()
    }
}