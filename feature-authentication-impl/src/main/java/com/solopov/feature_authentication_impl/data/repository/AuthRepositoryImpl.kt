package com.solopov.feature_authentication_impl.data.repository

import com.solopov.common.data.firebase.dao.UserFirebaseDao
import com.solopov.common.data.firebase.exceptions.AuthenticationException
import com.solopov.common.data.firebase.model.UserFirebase
import com.solopov.feature_authentication_api.domain.interfaces.AuthRepository
import com.solopov.feature_authentication_api.domain.model.User
import com.solopov.feature_authentication_impl.data.mappers.UserMappers
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor (
    private val userFirebaseDao: UserFirebaseDao,
    private val userMappers: UserMappers
): AuthRepository {

    override suspend fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ): User {

        return userMappers.mapUserFirebaseToUser(
            userFirebaseDao.createUser(
                email,
                password,
                name,
                age,
                gender,
            )
        )

    }



    override suspend fun signInUser(email: String?, password: String?): Boolean {
        return userFirebaseDao.signInUser(email, password)
    }
}
