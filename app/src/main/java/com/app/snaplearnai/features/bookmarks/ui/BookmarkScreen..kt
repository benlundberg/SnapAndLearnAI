package com.app.snaplearnai.features.bookmarks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.app.snaplearnai.R
import com.app.snaplearnai.features.bookmarks.ui.model.BookmarkUiState
import com.app.snaplearnai.features.bookmarks.ui.model.UiBookmarkItem
import com.app.snaplearnai.features.camera.ui.component.BottomSheetText
import com.app.snaplearnai.shared.ui.component.loading.CustomLoading
import com.app.snaplearnai.shared.ui.component.page.CustomScaffold
import com.app.snaplearnai.shared.ui.theme.AppTheme

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
    onBack: () -> Unit
) {

    val state by viewModel.uiStateFlow.collectAsState(initial = BookmarkUiState(isLoading = false))

    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedItem: UiBookmarkItem? by remember { mutableStateOf(null) }

    CustomScaffold(
        title = stringResource(R.string.bookmarks_title),
        onBack = onBack,
        snackbarEventFlow = viewModel.eventsFlow,
    ) {
        if (state.isLoading) {
            CustomLoading()
        } else {
            BookmarkContent(
                bookmarks = state.bookmarks,
                onSelect = {
                    selectedItem = it
                    isBottomSheetVisible = true
                }
            )
        }
    }

    BottomSheetText(
        text = selectedItem?.text ?: "",
        isBookmarked = selectedItem != null,
        showBottomSheet = isBottomSheetVisible,
        onDismiss = {
            isBottomSheetVisible = false
            selectedItem = null
        },
        onDelete = {
            selectedItem?.let {
                viewModel.onDelete(it.id)
            }
            isBottomSheetVisible = false
            selectedItem = null
        }
    )
}

@Composable
fun BookmarkContent(
    bookmarks: List<UiBookmarkItem>,
    onSelect: (UiBookmarkItem) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(AppTheme.spacings.m),
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacings.m),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.spacings.m)
    ) {
        items(items = bookmarks) { item ->
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable {
                        onSelect(item)
                    }
            ) {
                Column(
                    modifier = Modifier
                        .padding(AppTheme.spacings.m)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.spacings.xs)
                ) {
                    Text(text = item.date, style = AppTheme.typography.small)
                    Text(text = item.text, style = AppTheme.typography.medium, maxLines = 5)
                }
            }
        }
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

@Composable
private fun PreviewContent() {
    BookmarkContent(
        bookmarks = listOf(
            UiBookmarkItem(
                id = 1,
                text = "Text 1",
                title = "Title 1",
                originalText = "Original Text 1",
                date = "Date 1"
            ),
            UiBookmarkItem(
                id = 2,
                text = "Text 2",
                title = "Title 2",
                originalText = "Original Text 2",
                date = "Date 2"
            ),
            UiBookmarkItem(
                id = 3,
                text = "Text 3",
                title = "Title 3",
                originalText = "Original Text 3",
                date = "Date 3"
            )
        )
    ) { }
}