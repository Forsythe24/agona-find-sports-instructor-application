package com.solopov.feature_authentication_impl.data.repository

import com.solopov.common.data.network.api.AuthService
import com.solopov.common.data.network.jwt.JwtManager
import com.solopov.common.data.network.jwt.saveTokensFromResponse
import com.solopov.common.data.network.makeSafeApiCall
import com.solopov.common.data.network.model.CredentialsRemote
import com.solopov.common.data.network.model.SendNewPasswordOnEmailRequestDto
import com.solopov.common.data.network.model.UserSignUpRemote
import com.solopov.common.data.network.utils.NetworkStateProvider
import com.solopov.common.data.storage.UserDataStore
import com.solopov.feature_authentication_api.domain.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val jwtManager: JwtManager,
    private val userDataStore: UserDataStore,
    private val networkStateProvider: NetworkStateProvider,
) : AuthRepository {

    override suspend fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ) {

        makeSafeApiCall(networkStateProvider) {
            authService.createUser(
                UserSignUpRemote(
                    email, password, name, age, gender
                )
            )
        }

        val logInResult = makeSafeApiCall(networkStateProvider) {
            authService.logIn(CredentialsRemote(email, password))
        }

        jwtManager.saveTokensFromResponse(logInResult)
    }


    override suspend fun signInUser(email: String, password: String): Boolean {

        val response = makeSafeApiCall(networkStateProvider) {
            authService.logIn(CredentialsRemote(email, password))
        }

        jwtManager.saveTokensFromResponse(response)
        response.id?.let {
            userDataStore.saveUserId(it)
        }
        return true
    }

    override suspend fun sendNewPassword(email: String) {
        makeSafeApiCall(networkStateProvider) {
            authService.sendPassword(SendNewPasswordOnEmailRequestDto(email))
        }
    }
}
