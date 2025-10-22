package com.app.snaplearnai.shared.ui.component.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.snaplearnai.shared.ui.theme.AppTheme

@Composable
fun CustomDialog(
    title: String,
    text: String,
    confirmText: String,
    onConfirm: () -> Unit,
    dismissText: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = confirmText,
                    style = AppTheme.typography.default
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = dismissText,
                    style = AppTheme.typography.default
                )
            }
        },
        title = {
            Text(
                text = title,
                style = AppTheme.typography.medium
            )
        },
        text = {
            Text(
                text = text,
                style = AppTheme.typography.default
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun LightPreview() {
    AppTheme {
        CustomDialog(
            title = "Title",
            text = "Text for dialog",
            confirmText = "Confirm",
            onConfirm = {},
            dismissText = "Dismiss",
            onDismiss = {}
        )
    }
}