package com.solopov.common.data.db.di

import com.solopov.common.data.db.AppDatabase

interface DbApi {

    fun provideDatabase(): AppDatabase
}
