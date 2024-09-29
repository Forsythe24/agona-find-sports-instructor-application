package com.solopov.common.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "rating",
    indices = [Index(value = ["instructor_id", "user_id"], unique = true)]
)
data class RatingLocal(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,

    @ColumnInfo(name = "instructor_id")
    val instructorId: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    val rating: Float,
)
