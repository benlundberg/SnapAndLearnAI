package com.app.snaplearnai.features.bookmarks.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        BookmarkSourceModule::class
    ]
)
@InstallIn(SingletonComponent::class)
class BookmarkModule