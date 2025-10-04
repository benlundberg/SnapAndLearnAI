package com.app.snaplearnai.shared.ui.component.notification

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.app.snaplearnai.shared.ui.model.BaseEvent
import com.app.snaplearnai.shared.ui.model.OneTimeEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun CustomSnackBarEffect(
    snackbarEventFlow: Flow<OneTimeEvent>?,
    snackBarHostState: CustomSnackBarHostState,
    onEvent: ((OneTimeEvent) -> Unit)? = null
) {
    if (snackbarEventFlow != null) {
        LaunchedEffect(Unit) {
            snackbarEventFlow.collect {
                onEvent?.invoke(it)
                when (it) {
                    is BaseEvent.ShowMessage -> {
                        snackBarHostState.showSnackBar(message = it.message, type = it.type)
                    }

                    is BaseEvent.ShowError -> {
                        snackBarHostState.showSnackBar(
                            message = it.message,
                            type = SnackBarType.ERROR
                        )
                    }

                    is BaseEvent.ShowSuccess -> {
                        snackBarHostState.showSnackBar(
                            message = it.message,
                            type = SnackBarType.SUCCESS,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }
}