package com.app.snaplearnai.shared.ui.component.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.snaplearnai.shared.ui.theme.AppTheme
import com.app.snaplearnai.shared.ui.theme.lighter

@Composable
fun CustomLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.color.black.lighter(.5f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview(showBackground = true)
private fun LightPreview() {
    AppTheme {
        CustomLoading()
    }
}