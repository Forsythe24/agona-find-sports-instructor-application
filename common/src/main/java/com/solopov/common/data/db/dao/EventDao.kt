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

    @Query("SELECT * FROM event WHERE date = :dateLong")
    abstract fun getAllEventsByDate(dateLong: Long): List<EventLocal>?

    @Query("DELETE FROM event WHERE id = :id")
    abstract fun deleteEventById(id: Long)

    @Query("DELETE FROM event WHERE :dateLong - date >= 259200000")
    abstract fun deleteAllEventsThreeOrMoreDaysAgo(dateLong: Long)
}
