package com.solopov.feature_authentication_impl.data.repository

import com.solopov.common.data.remote.dao.UserRemoteDao
import com.solopov.feature_authentication_api.domain.interfaces.AuthRepository
import com.solopov.feature_authentication_impl.data.mappers.UserMappers
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userRemoteDao: UserRemoteDao,
    private val userMappers: UserMappers
) : AuthRepository {

    override suspend fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ) {
        userRemoteDao.createUser(
            email,
            password,
            name,
            age,
            gender,
        )
    }


    override suspend fun signInUser(email: String?, password: String?): Boolean {
        return userRemoteDao.signInUser(email, password)
    }

    override suspend fun sendNewPassword(email: String) {
        return userRemoteDao.sendPassword(email)
    }
}
