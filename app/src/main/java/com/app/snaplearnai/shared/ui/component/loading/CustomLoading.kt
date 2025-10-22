package com.app.snaplearnai.shared.ui.component.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.app.snaplearnai.R
import com.app.snaplearnai.shared.ui.theme.AppTheme

@Composable
fun CustomLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacings.xs)
        ) {
            CircularProgressIndicator(
                color = AppTheme.color.primary, modifier = Modifier.align(
                    Alignment.CenterHorizontally
                )
            )
            Text(text = stringResource(R.string.gen_loading), style = AppTheme.typography.default)
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun LightPreview() {
    AppTheme {
        CustomLoading()
    }
}