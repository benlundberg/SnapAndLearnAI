package com.app.snaplearnai.features.camera.di

import com.app.snaplearnai.features.camera.data.service.AIServiceImpl
import com.app.snaplearnai.features.camera.data.service.GeminiServiceImpl
import com.app.snaplearnai.features.camera.data.source.CameraRepositoryImpl
import com.app.snaplearnai.features.camera.domain.CameraRepository
import com.app.snaplearnai.features.camera.domain.service.AIService
import com.app.snaplearnai.features.camera.domain.service.GeminiService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CameraSourceModule {

    @Binds
    abstract fun bindCameraRepository(impl: CameraRepositoryImpl): CameraRepository

    @Binds
    abstract fun bindAIService(impl: AIServiceImpl): AIService

    @Binds
    abstract fun bindGeminiService(impl: GeminiServiceImpl): GeminiService
}