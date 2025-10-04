package com.app.snaplearnai.features.camera.ui.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.app.snaplearnai.features.camera.ui.CameraScreen
import com.app.snaplearnai.features.quiz.ui.nav.QuizRoute

object CameraRoute {
    const val CAMERA = "camera"
}

fun NavGraphBuilder.cameraNav(
    navHostController: NavHostController
) {
    composable(CameraRoute.CAMERA) {
        CameraScreen(
            onQuiz = { questionJson ->
                // Navigate to quiz screen
                navHostController.navigate("${QuizRoute.QUIZ}/${questionJson}")
            }
        )
    }
}