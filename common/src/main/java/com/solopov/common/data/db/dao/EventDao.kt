package com.solopov.common.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solopov.common.data.db.model.EventLocal
import com.solopov.common.data.db.model.RatingLocal
import java.util.Date

@Dao
abstract class EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addEvent(event: EventLocal)

    @Query("SELECT * FROM event WHERE date = :date")
    abstract fun getAllEventsByDate(date: Date): List<EventLocal>?
}
