package com.solopov.common.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solopov.common.data.db.model.RatingLocal

@Dao
abstract class RatingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addRating(ratingLocal: RatingLocal)

    @Query("SELECT * FROM rating WHERE user_id = :userId AND instructor_id = :instructorId")
    abstract fun getRatingByUserAndInstructorIds(userId: String, instructorId: String): RatingLocal?

    @Query("SELECT * FROM rating WHERE instructor_id = :instructorId")
    abstract fun getAllInstructorRatingsById(instructorId: String): List<RatingLocal>?
}
