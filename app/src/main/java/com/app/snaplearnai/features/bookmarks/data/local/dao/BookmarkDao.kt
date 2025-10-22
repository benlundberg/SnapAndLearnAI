package com.app.snaplearnai.features.bookmarks.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.snaplearnai.features.bookmarks.data.local.entities.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarksFlow(): Flow<List<BookmarkEntity>>?

    @Query("SELECT * FROM bookmarks")
    suspend fun getAllBookmarks(): List<BookmarkEntity>

    @Query("DELETE FROM bookmarks WHERE id = :bookmarkId")
    suspend fun deleteBookmark(bookmarkId: Int)

    @Query("SELECT * FROM bookmarks WHERE id = :bookmarkId")
    suspend fun getBookmark(bookmarkId: Int): BookmarkEntity?
}