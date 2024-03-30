package com.solopov.feature_authentication_api.domain.interfaces

interface AuthRepository {

    fun createUser(email: String, password: String)
}
