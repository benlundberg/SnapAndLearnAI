package com.app.snaplearnai.shared.ui.component.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.snaplearnai.shared.ui.theme.AppTheme

private val SNACKBAR_HEIGHT = 56.dp

@Composable
fun CustomSnackBar(
    message: String,
    type: SnackBarType,
    duration: SnackbarDuration,
    onClose: () -> Unit
) {
    val config = when (type) {
        SnackBarType.INFO -> Pair(
            AppTheme.color.skyBlue,
            Icons.Filled.Info
        )

        SnackBarType.ERROR -> Pair(
            AppTheme.color.brickRed,
            Icons.Filled.Warning
        )

        SnackBarType.SUCCESS -> Pair(
            AppTheme.color.grassGreen,
            Icons.Filled.CheckCircle
        )

        SnackBarType.WARNING -> Pair(
            AppTheme.color.sunYellow,
            Icons.Filled.Warning
        )
    }

    Row(
        modifier = Modifier
            .background(config.first)
            .fillMaxWidth()
            .height(SNACKBAR_HEIGHT)
            .padding(horizontal = AppTheme.spacings.m),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacings.m),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = config.second, contentDescription = null)
        Text(
            text = message,
            style = AppTheme.typography.default,
            modifier = Modifier.weight(1f)
        )
        if (duration == SnackbarDuration.Indefinite) {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomSnackBarPreview() {
    Column {
        CustomSnackBar(
            message = "Message",
            type = SnackBarType.INFO,
            duration = SnackbarDuration.Long
        ) {

        }
        CustomSnackBar(
            message = "Message",
            type = SnackBarType.ERROR,
            duration = SnackbarDuration.Indefinite
        ) {

        }
        CustomSnackBar(
            message = "Message",
            type = SnackBarType.SUCCESS,
            duration = SnackbarDuration.Long
        ) {

        }
        CustomSnackBar(
            message = "Message",
            type = SnackBarType.WARNING,
            duration = SnackbarDuration.Long
        ) {

        }
    }
}