package com.solopov.common.data.network.di

import com.solopov.common.data.network.dao.ChatRemoteDao
import com.solopov.common.data.network.dao.UserRemoteDao
import com.solopov.common.data.network.jwt.JwtManager

interface RemoteApi {
    fun provideUserRemoteDao(): UserRemoteDao
    fun provideChatRemoteDao(): ChatRemoteDao
    fun provideJwtManager(): JwtManager
}
