package com.app.snaplearnai.features.bookmarks.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String?,
    val text: String,
    val originalText: String,
    val timestamp: Long
)