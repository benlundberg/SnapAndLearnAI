package com.app.snaplearnai.shared.ui.component.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.snaplearnai.shared.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String? = null,
    onBack: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    leadingAction: @Composable (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            title?.let {
                Text(text = title, style = AppTheme.typography.large)
            }
        },
        navigationIcon = {
            onBack?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            } ?: leadingAction?.let {
                it()
            }
        },
        actions = actions?.let { { it() } } ?: {}
    )
}

@Preview(showBackground = true)
@Composable
private fun LightPreview() {
    AppTheme {
        CustomTopBar(
            title = "Title",
            onBack = {

            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DarkPreview() {
    AppTheme(
        darkTheme = true
    ) {
        CustomTopBar(
            title = "Title",
            onBack = {

            }
        )
    }
}