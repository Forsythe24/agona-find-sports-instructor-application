package com.solopov.common.data.remote.di

import com.solopov.common.data.remote.dao.ChatRemoteDao
import com.solopov.common.data.remote.dao.UserRemoteDao

interface RemoteApi {
    fun provideUserRemoteDao(): UserRemoteDao
    fun provideChatRemoteDao(): ChatRemoteDao
}