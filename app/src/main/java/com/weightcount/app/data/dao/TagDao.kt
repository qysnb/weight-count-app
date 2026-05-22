package com.weightcount.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.weightcount.app.data.entity.RecordTagCrossRef
import com.weightcount.app.data.entity.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Query("SELECT * FROM tags ORDER BY sortOrder ASC")
    fun getAllTags(): Flow<List<Tag>>

    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun getTagById(id: Long): Tag?

    @Query("SELECT MAX(sortOrder) FROM tags")
    suspend fun getMaxSortOrder(): Int?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTag(tag: Tag): Long

    @Update
    suspend fun updateTag(tag: Tag)

    @Delete
    suspend fun deleteTag(tag: Tag)

    @Query("DELETE FROM tags WHERE id = :id")
    suspend fun deleteTagById(id: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRef(crossRef: RecordTagCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllCrossRefs(crossRefs: List<RecordTagCrossRef>)

    @Query("DELETE FROM record_tag_cross_ref WHERE recordId = :recordId")
    suspend fun deleteCrossRefsForRecord(recordId: Long)

    @Query("SELECT tagId FROM record_tag_cross_ref WHERE recordId = :recordId")
    suspend fun getTagIdsForRecord(recordId: Long): List<Long>
}
