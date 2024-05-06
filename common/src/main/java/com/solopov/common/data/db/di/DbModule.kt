package com.solopov.common.data.db.di

import android.content.Context
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.db.dao.RatingDao
import com.solopov.common.data.db.dao.UserDao
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
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @ApplicationScope
    fun provideRatingDao(appDatabase: AppDatabase): RatingDao {
        return appDatabase.ratingDao()
    }
}
