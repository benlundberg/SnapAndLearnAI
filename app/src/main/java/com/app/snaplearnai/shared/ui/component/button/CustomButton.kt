package com.app.snaplearnai.shared.ui.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.snaplearnai.shared.ui.theme.AppTheme

private val BUTTON_HEIGHT = 56.dp

enum class ButtonStyle {
    PRIMARY,
    SECONDARY
}


@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: ButtonStyle = ButtonStyle.PRIMARY,
    buttonColors: ButtonColors? = null
) {
    val colors = buttonColors ?: when (style) {
        ButtonStyle.PRIMARY -> ButtonDefaults.buttonColors(
            containerColor = AppTheme.color.primary,
            contentColor = AppTheme.color.white
        )
        ButtonStyle.SECONDARY -> ButtonDefaults.buttonColors(
            containerColor = AppTheme.color.secondary,
            contentColor = AppTheme.color.white
        )
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .defaultMinSize(minHeight = BUTTON_HEIGHT),
        colors = colors,
        enabled = enabled
    ) {
        Text(
            text = text,
            style = AppTheme.typography.medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun LightPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .padding(AppTheme.spacings.m)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacings.m)
        ) {
            CustomButton(
                text = "Primary",
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            )
            CustomButton(
                text = "Secondary",
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                style = ButtonStyle.SECONDARY
            )
            CustomButton(
                text = "Disabled",
                onClick = { },
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}