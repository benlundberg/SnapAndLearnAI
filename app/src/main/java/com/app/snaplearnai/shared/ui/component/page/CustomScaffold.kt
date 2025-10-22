package com.app.snaplearnai.shared.ui.component.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.snaplearnai.shared.ui.component.notification.CustomSnackBarEffect
import com.app.snaplearnai.shared.ui.component.notification.CustomSnackBarHost
import com.app.snaplearnai.shared.ui.component.notification.CustomSnackBarHostState
import com.app.snaplearnai.shared.ui.component.topbar.CustomTopBar
import com.app.snaplearnai.shared.ui.model.OneTimeEvent
import com.app.snaplearnai.shared.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun CustomScaffold(
    title: String? = null,
    onBack: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    leadingAction: @Composable (() -> Unit)? = null,
    snackbarEventFlow: Flow<OneTimeEvent>? = null,
    onEvent: ((OneTimeEvent) -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    val snackBarHostState: CustomSnackBarHostState = remember { CustomSnackBarHostState() }

    CustomSnackBarEffect(
        snackbarEventFlow = snackbarEventFlow,
        snackBarHostState = snackBarHostState,
        onEvent = onEvent
    )

    val hasTopBar = title != null || onBack != null || actions != null || leadingAction != null

    Scaffold(
        topBar = {
            if (hasTopBar) {
                CustomTopBar(
                    title = title,
                    onBack = onBack,
                    actions = actions,
                    leadingAction = leadingAction
                )
            }
        },
        snackbarHost = { CustomSnackBarHost(snackBarHostState = snackBarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
        ) {
            content(innerPadding)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LightPreview() {
    AppTheme {
        CustomScaffold(
            title = "Page title",
            onBack = {

            }
        ) {
            Text(text = "Content")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        CustomScaffold(
            title = "Page title",
            onBack = {

            }
        ) {
            Text(text = "Content")
        }
    }
}