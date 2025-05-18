package com.solopov.feature_authentication_api.di

import com.solopov.feature_authentication_api.domain.AuthRepository

interface AuthFeatureApi {
    fun provideAuthRepository(): AuthRepository
}
