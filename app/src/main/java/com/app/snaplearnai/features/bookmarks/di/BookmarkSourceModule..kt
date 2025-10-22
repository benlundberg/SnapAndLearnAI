package com.app.snaplearnai.features.bookmarks.di

import com.app.snaplearnai.features.bookmarks.data.source.BookmarkRepositoryImpl
import com.app.snaplearnai.features.bookmarks.domain.BookmarkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class BookmarkSourceModule {

    @Binds
    abstract fun bindBookmarkRepository(
        bookmarkItemRepositoryImpl: BookmarkRepositoryImpl
    ): BookmarkRepository
}