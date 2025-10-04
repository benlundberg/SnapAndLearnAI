package com.app.snaplearnai.shared.ui.component.notification

import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable

@Composable
fun CustomSnackBarHost(
    snackBarHostState: CustomSnackBarHostState
) {
    SnackbarHost(hostState = snackBarHostState.snackBarHostState) {
        CustomSnackBar(
            message = it.visuals.message,
            type = snackBarHostState.type,
            duration = it.visuals.duration,
            onClose = {
                it.dismiss()
            }
        )
    }
}