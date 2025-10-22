package com.app.snaplearnai.shared.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.app.snaplearnai.features.bookmarks.ui.nav.bookmarkNav
import com.app.snaplearnai.features.camera.ui.nav.CameraRoute
import com.app.snaplearnai.features.camera.ui.nav.cameraNav
import com.app.snaplearnai.features.quiz.ui.nav.quizNav

@Composable
fun NavigationComponent(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = CameraRoute.CAMERA
    ) {
        cameraNav(navHostController)
        quizNav(navHostController)
        bookmarkNav(navHostController)
    }
}