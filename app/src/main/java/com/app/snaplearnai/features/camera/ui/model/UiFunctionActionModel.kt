package com.app.snaplearnai.features.camera.ui.model

data class UiFunctionActionModel(
    val label: String,
    val type: FunctionActionType,
    val onClick: (FunctionActionType) -> Unit
)