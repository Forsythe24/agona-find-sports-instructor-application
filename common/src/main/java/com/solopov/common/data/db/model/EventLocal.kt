package com.solopov.common.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "event", indices = [Index(value = ["person_name", "name", "date", "start_time", "end_time", "place"], unique = true)])
data class EventLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    @ColumnInfo(name = "person_name")
    val personName: String?,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "start_time")
    val startTime: Int?,
    @ColumnInfo(name = "end_time")
    val endTime: Int?,
    val place: String?,
)
