package com.solopov.common.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solopov.common.data.db.converter.DateConverter
import com.solopov.common.data.db.dao.EventDao
import com.solopov.common.data.db.dao.RatingDao
import com.solopov.common.data.db.model.EventLocal
import com.solopov.common.data.db.model.RatingLocal

private const val DATABASE_NAME = "app.db"

@Database(
    version = 1,
    entities = [
        RatingLocal::class,
        EventLocal::class,
    ]
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        fun get(context: Context): AppDatabase = Room
            .databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    abstract fun ratingDao(): RatingDao

    abstract fun eventDao(): EventDao
}
