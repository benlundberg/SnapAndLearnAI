package com.app.snaplearnai.features.bookmarks.ui.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.app.snaplearnai.features.bookmarks.ui.BookmarkScreen

object BookmarkRoute {
    const val BOOKMARK = "bookmark"
}

fun NavGraphBuilder.bookmarkNav(
    navHostController: NavHostController
) {
    composable(BookmarkRoute.BOOKMARK) {
        BookmarkScreen(onBack = {
            navHostController.navigateUp()
        })
    }
}