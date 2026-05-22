package com.weightcount.app.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class WeightRecordWithTags(
    @Embedded val record: WeightRecord,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = RecordTagCrossRef::class,
            parentColumn = "recordId",
            entityColumn = "tagId"
        )
    )
    val tags: List<Tag>
)
