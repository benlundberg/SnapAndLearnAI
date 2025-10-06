package com.app.snaplearnai.features.camera.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.snaplearnai.R
import com.app.snaplearnai.shared.ui.component.button.CustomButton
import com.app.snaplearnai.shared.ui.theme.AppTheme

@Composable
fun CameraPermissionRationale(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.spacings.m),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.CameraAlt,
            contentDescription = "Camera",
            modifier = Modifier.size(32.dp)
        )
        Spacer(Modifier.height(AppTheme.spacings.l))
        Text(
            text = stringResource(R.string.camera_permission_message),
            style = AppTheme.typography.default,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(AppTheme.spacings.l))
        CustomButton(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.grant_permission_btn)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun LightPreview() {
    AppTheme {
        CameraPermissionRationale {

        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DarkPreview() {
    AppTheme(darkTheme = true) {
        Surface {
            CameraPermissionRationale {

            }
        }
    }
}