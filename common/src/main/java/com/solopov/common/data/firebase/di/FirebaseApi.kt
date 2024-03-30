package com.solopov.common.data.firebase.di

import com.solopov.common.data.firebase.dao.UserFirebaseDao

interface FirebaseApi {
    fun provideUserFirebaseDao(): UserFirebaseDao
}