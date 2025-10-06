package com.app.snaplearnai.features.camera.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Speaker
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.snaplearnai.features.camera.ui.model.FunctionActionType
import com.app.snaplearnai.features.camera.ui.model.UiFunctionActionModel
import com.app.snaplearnai.shared.ui.theme.AppTheme
import com.app.snaplearnai.shared.ui.theme.semiBold
import kotlinx.coroutines.delay

@Composable
fun FunctionActionsView(
    actions: List<UiFunctionActionModel>,
    isRecognizeComplete: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = AppTheme.spacings.m, vertical = AppTheme.spacings.l),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacings.s)
    ) {
        var isButtonsTriggered by remember { mutableStateOf(false) }
        val resetButtonsAnimation = { isButtonsTriggered = false }

        LaunchedEffect(isRecognizeComplete) {
            if (isRecognizeComplete) {
                isButtonsTriggered = true
            }
        }

        if (isButtonsTriggered) {
            LaunchedEffect(true) {
                delay(500)
                resetButtonsAnimation()
            }
        }

        // Buttons
        actions.forEach { actionModel ->
            TextFunctionButton(
                label = actionModel.label,
                type = actionModel.type,
                isEnabled = isRecognizeComplete,
                isTriggered = isButtonsTriggered,
                onClick = actionModel.onClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TextFunctionButton(
    label: String,
    type: FunctionActionType,
    isEnabled: Boolean,
    isTriggered: Boolean,
    onClick: (FunctionActionType) -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonColor by animateColorAsState(
        targetValue = if (isEnabled) AppTheme.color.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "ButtonColorChange"
    )

    val scale by animateFloatAsState(
        targetValue = if (isTriggered) 1.1f else 1.0f, // 10% larger when triggered
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        label = "ButtonScale"
    )

    val verticalOffset by animateDpAsState(
        targetValue = if (isTriggered) (-10).dp else 0.dp, // Move up 10dp when triggered
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        label = "ButtonOffset"
    )

    TextButton(
        onClick = {
            if (isEnabled) {
                onClick.invoke(type)
            }
        },
        border = BorderStroke(
            width = 1.dp,
            color = buttonColor
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationY = verticalOffset.value
            )
    ) {
        Column(
            modifier = Modifier.padding(vertical = AppTheme.spacings.s),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacings.xs),
        ) {
            Icon(
                imageVector = when (type) {
                    FunctionActionType.SUMMARY -> Icons.Outlined.Book
                    FunctionActionType.TRANSLATE -> Icons.Default.Translate
                    FunctionActionType.EXPLAIN -> Icons.Default.Speaker
                    FunctionActionType.QUIZ -> Icons.Default.Quiz
                },
                contentDescription = label,
                tint = buttonColor,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterHorizontally),
            )
            Text(
                text = label,
                color = buttonColor,
                style = AppTheme.typography.micro.semiBold,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun LightPreview() {
    AppTheme {
        Surface {
            PreviewContent()
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DarkPreview() {
    AppTheme(darkTheme = true) {
        Surface {
            PreviewContent()
        }
    }
}

@Composable
private fun PreviewContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        FunctionActionsView(
            actions = listOf(
                UiFunctionActionModel(
                    label = "Summary",
                    type = FunctionActionType.SUMMARY,
                    onClick = { }
                ),
                UiFunctionActionModel(
                    label = "Translate",
                    type = FunctionActionType.TRANSLATE,
                    onClick = { }
                ),
                UiFunctionActionModel(
                    label = "Explain",
                    type = FunctionActionType.EXPLAIN,
                    onClick = { }
                ),
                UiFunctionActionModel(
                    label = "Quizify",
                    type = FunctionActionType.QUIZ,
                    onClick = { }
                )
            ),
            isRecognizeComplete = false
        )
    }
}