package com.app.snaplearnai.features.camera.ui.component

import android.content.ClipData
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.app.snaplearnai.R
import com.app.snaplearnai.shared.ui.component.button.IconLabelButton
import com.app.snaplearnai.shared.ui.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetText(
    text: String,
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    onBookmark: ((Boolean, String) -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    isBookmarked: Boolean = false
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val scope = rememberCoroutineScope()

    // Show the ModalBottomSheet when the state is true
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                onDismiss()
            },
            properties = ModalBottomSheetProperties(
                shouldDismissOnBackPress = false,
                shouldDismissOnClickOutside = false
            ),
            sheetState = sheetState
        ) {
            BottomSheetContent(
                text = text,
                bookmarked = isBookmarked,
                onBookmark = onBookmark,
                onDelete = onDelete,
                onDismiss = {
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onDismiss()
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetContent(
    text: String,
    bookmarked: Boolean,
    onDismiss: () -> Unit,
    onBookmark: ((Boolean, String) -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .padding(AppTheme.spacings.m)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val localClipBoard = LocalClipboard.current
            val clipBoardScope = rememberCoroutineScope()

            IconLabelButton(
                onClick = {
                    val clipData = ClipData.newPlainText("text", text)
                    clipBoardScope.launch { localClipBoard.setClipEntry(clipData.toClipEntry()) }
                },
                icon = Icons.Default.CopyAll,
                label = stringResource(R.string.gen_copy)
            )

            val context = LocalContext.current

            IconLabelButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }

                    val shareIntent = Intent.createChooser(intent, null)
                    context.startActivity(shareIntent)
                },
                icon = Icons.Default.Share,
                label = stringResource(R.string.gen_share)
            )

            var isBookmarked by remember { mutableStateOf(bookmarked) }

            if (onBookmark != null) {
                IconLabelButton(
                    onClick = {
                        isBookmarked = !isBookmarked
                        onBookmark(isBookmarked, text)
                    },
                    icon = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkAdd,
                    label = stringResource(R.string.gen_bookmark)
                )
            } else if (onDelete != null) {
                IconLabelButton(
                    onClick = {
                        onDelete()
                    },
                    icon = Icons.Filled.Delete,
                    label = stringResource(R.string.gen_delete)
                )
            }

            IconLabelButton(
                onClick = onDismiss,
                icon = Icons.Default.Cancel,
                label = stringResource(R.string.gen_dismiss)
            )
        }
        Spacer(Modifier.height(AppTheme.spacings.xl))

        Text(
            text = text,
            style = AppTheme.typography.default,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )

        Spacer(Modifier.height(AppTheme.spacings.m)) // Extra padding at the bottom
    }
}

@Composable
@Preview(showBackground = true)
private fun LightPreview() {
    AppTheme {
        PreviewContent()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PreviewContent() {
    BottomSheetContent(
        text = "Text in bottom sheet",
        bookmarked = true,
        onDismiss = { },
        onBookmark = { _, _ -> },
        onDelete = { }
    )
}