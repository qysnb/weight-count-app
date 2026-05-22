package com.weightcount.app.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "record_tag_cross_ref",
    primaryKeys = ["recordId", "tagId"],
    foreignKeys = [
        ForeignKey(
            entity = WeightRecord::class,
            parentColumns = ["id"],
            childColumns = ["recordId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Tag::class,
            parentColumns = ["id"],
            childColumns = ["tagId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["tagId"])
    ]
)
data class RecordTagCrossRef(
    val recordId: Long,
    val tagId: Long
)
