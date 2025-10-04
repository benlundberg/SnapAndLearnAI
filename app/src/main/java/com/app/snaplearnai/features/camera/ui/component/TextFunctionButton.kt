package com.app.snaplearnai.features.camera.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.snaplearnai.features.camera.ui.model.FunctionActionType
import com.app.snaplearnai.shared.ui.theme.AppTheme

@Composable
fun TextFunctionButton(
    label: String,
    type: FunctionActionType,
    isEnabled: Boolean,
    isTriggered: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val buttonColor by animateColorAsState(
        targetValue = if (isEnabled) Color.Green else Color.White,
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

    Surface(
        color = Color.Transparent,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationY = verticalOffset.value
            )
            .border(
                width = 3.dp,
                color = buttonColor,
                shape = MaterialTheme.shapes.medium
            )
            .padding(vertical = AppTheme.spacings.l),
        onClick = onClick
    ) {
        Column(
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
                style = AppTheme.typography.micro,
                textAlign = TextAlign.Center,
            )
        }
    }
}