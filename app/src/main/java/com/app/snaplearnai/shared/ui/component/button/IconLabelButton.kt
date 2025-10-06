package com.app.snaplearnai.shared.ui.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.app.snaplearnai.shared.ui.theme.AppTheme

@Composable
fun IconLabelButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        modifier = modifier.padding(AppTheme.spacings.xs)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacings.xs),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
            )

            Text(
                text = label,
                style = AppTheme.typography.micro,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun LightPreview() {
    AppTheme {
        IconLabelButton(
            icon = Icons.Default.Add,
            label = "Add",
            onClick = { }
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun DarkPreview() {
    AppTheme(darkTheme = true) {
        Surface {
            IconLabelButton(
                icon = Icons.Default.Bookmark,
                label = "Bookmark",
                onClick = { }
            )
        }
    }
}