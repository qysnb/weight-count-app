package com.weightcount.app.data.repository

import com.weightcount.app.data.dao.TagDao
import com.weightcount.app.data.dao.WeightDao
import com.weightcount.app.data.entity.RecordTagCrossRef
import com.weightcount.app.data.entity.Tag
import com.weightcount.app.data.entity.WeightRecord
import com.weightcount.app.data.entity.WeightRecordWithTags
import kotlinx.coroutines.flow.Flow

class WeightRepository(
    private val weightDao: WeightDao,
    private val tagDao: TagDao
) {
    fun getAllRecordsWithTags(): Flow<List<WeightRecordWithTags>> =
        weightDao.getAllRecordsWithTags()

    fun getRecordsForDay(startOfDay: Long, endOfDay: Long): Flow<List<WeightRecordWithTags>> =
        weightDao.getRecordsForDay(startOfDay, endOfDay)

    fun getRecordsInRange(startTime: Long, endTime: Long): Flow<List<WeightRecordWithTags>> =
        weightDao.getRecordsInRange(startTime, endTime)

    fun getRecordsInRangeWithTag(startTime: Long, endTime: Long, tagId: Long): Flow<List<WeightRecordWithTags>> =
        weightDao.getRecordsInRangeWithTag(startTime, endTime, tagId)

    suspend fun getRecordWithTagsById(id: Long): WeightRecordWithTags? =
        weightDao.getRecordWithTagsById(id)

    suspend fun addRecord(weight: Double, timestamp: Long, tagIds: List<Long>, note: String? = null): Long {
        val recordId = weightDao.insertRecord(
            WeightRecord(weight = weight, timestamp = timestamp, note = note)
        )
        if (tagIds.isNotEmpty()) {
            tagDao.insertAllCrossRefs(tagIds.map { RecordTagCrossRef(recordId, it) })
        }
        return recordId
    }    suspend fun updateRecord(id: Long, weight: Double, timestamp: Long, tagIds: List<Long>, note: String? = null) {
        weightDao.updateRecord(WeightRecord(id = id, weight = weight, timestamp = timestamp, note = note))
        tagDao.deleteCrossRefsForRecord(id)
        if (tagIds.isNotEmpty()) {
            tagDao.insertAllCrossRefs(tagIds.map { RecordTagCrossRef(id, it) })
        }
    }

    suspend fun deleteRecord(id: Long) {
        weightDao.deleteRecordById(id)
    }

    suspend fun saveTagsForRecord(recordId: Long, tagIds: List<Long>) {
        tagDao.deleteCrossRefsForRecord(recordId)
        if (tagIds.isNotEmpty()) {
            tagDao.insertAllCrossRefs(tagIds.map { RecordTagCrossRef(recordId, it) })
        }
    }
}
