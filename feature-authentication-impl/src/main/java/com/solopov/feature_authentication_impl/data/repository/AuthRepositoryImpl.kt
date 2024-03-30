package com.solopov.feature_authentication_impl.data.repository

import com.solopov.common.data.firebase.dao.UserFirebaseDao
import com.solopov.feature_authentication_api.domain.interfaces.AuthRepository
import com.solopov.feature_authentication_impl.data.mappers.UserMappers
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor (
    private val userFirebaseDao: UserFirebaseDao,
    private val userMappers: UserMappers
): AuthRepository {

    override fun createUser(email: String, password: String) {
//        userFirebaseDao.initializeApp()
        userFirebaseDao.createUserWithEmailAndPassword(email, password)
    }
}
