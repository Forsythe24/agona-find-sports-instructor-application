package com.solopov.common.data.db.di

import android.content.Context
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.db.dao.EventDao
import com.solopov.common.data.db.dao.RatingDao
import com.solopov.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DbModule {

    @Provides
    @ApplicationScope
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.get(context)
    }

    @Provides
    @ApplicationScope
    fun provideRatingDao(appDatabase: AppDatabase): RatingDao {
        return appDatabase.ratingDao()
    }

    @Provides
    @ApplicationScope
    fun provideEventDao(appDatabase: AppDatabase): EventDao {
        return appDatabase.eventDao()
    }
}
