package com.weightcount.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_records")
data class WeightRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val weight: Double,
    val timestamp: Long,
    val note: String? = null
)
