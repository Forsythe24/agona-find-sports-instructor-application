package com.solopov.feature_user_profile_impl.di

import com.google.firebase.storage.FirebaseStorage
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.db.AppDatabase
import com.solopov.common.data.network.api.RefreshTokenService
import com.solopov.common.data.network.api.UserApiService
import com.solopov.common.data.network.jwt.JwtManager
import com.solopov.common.data.storage.UserDataStore
import com.solopov.common.utils.DateFormatter

interface UserProfileFeatureDependencies {
    fun resourceManager(): ResourceManager
    fun db(): AppDatabase
    fun jwtManager(): JwtManager
    fun refreshTokenService(): RefreshTokenService
    fun dateFormatter(): DateFormatter
    fun userDataStore(): UserDataStore
    fun userApiService(): UserApiService
    fun firebaseStorage(): FirebaseStorage
}
