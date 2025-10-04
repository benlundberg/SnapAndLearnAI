package com.app.snaplearnai.features.quiz.ui.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.app.snaplearnai.features.quiz.ui.QuizScreen

object QuizRoute {
    const val QUIZ = "quiz"
}

object QuizArguments {
    const val QUIZ_ARGS = "questions"
}

fun NavGraphBuilder.quizNav(
    navHostController: NavHostController
) {
    composable("${QuizRoute.QUIZ}/{${QuizArguments.QUIZ_ARGS}}") {
        QuizScreen(
            onBack = {
                navHostController.navigateUp()
            }
        )
    }
}