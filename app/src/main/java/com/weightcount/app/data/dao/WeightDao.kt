package com.weightcount.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.weightcount.app.data.entity.WeightRecord
import com.weightcount.app.data.entity.WeightRecordWithTags
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightDao {

    @Transaction
    @Query("SELECT * FROM weight_records ORDER BY timestamp DESC")
    fun getAllRecordsWithTags(): Flow<List<WeightRecordWithTags>>

    @Transaction
    @Query("SELECT * FROM weight_records WHERE timestamp >= :startOfDay AND timestamp < :endOfDay ORDER BY timestamp DESC")
    fun getRecordsForDay(startOfDay: Long, endOfDay: Long): Flow<List<WeightRecordWithTags>>

    @Transaction
    @Query("SELECT * FROM weight_records WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp ASC")
    fun getRecordsInRange(startTime: Long, endTime: Long): Flow<List<WeightRecordWithTags>>

    @Transaction
    @Query("SELECT * FROM weight_records WHERE id = :id")
    suspend fun getRecordWithTagsById(id: Long): WeightRecordWithTags?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: WeightRecord): Long

    @Update
    suspend fun updateRecord(record: WeightRecord)

    @Delete
    suspend fun deleteRecord(record: WeightRecord)

    @Query("DELETE FROM weight_records WHERE id = :id")
    suspend fun deleteRecordById(id: Long)
}
