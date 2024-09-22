package com.solopov.feature_authentication_impl.data.repository

import android.util.Patterns
import com.solopov.common.data.network.api.AuthService
import com.solopov.common.data.network.exceptions.AuthException
import com.solopov.common.data.network.jwt.JwtManager
import com.solopov.common.data.network.jwt.saveTokensFromResponse
import com.solopov.common.data.network.model.CredentialsRemote
import com.solopov.common.data.network.model.SendNewPasswordOnEmailRequestDto
import com.solopov.common.data.network.model.UserSignUpRemote
import com.solopov.common.data.network.utils.handleApiErrors
import com.solopov.common.data.network.utils.makeSafeApiCall
import com.solopov.common.data.storage.UserDataStore
import com.solopov.feature_authentication_api.domain.interfaces.AuthRepository
import java.net.HttpURLConnection
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val jwtManager: JwtManager,
    private val userDataStore: UserDataStore,
) : AuthRepository {

    override suspend fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ) {

        makeSafeApiCall {
            authService.createUser(
                UserSignUpRemote(
                    email, password, name, age, gender
                )
            )
        }.handleApiErrors(
            hashMapOf(
                HttpURLConnection.HTTP_CONFLICT to AuthException.EmailAlreadyInUseException("User with such email ($email) already exists")
            )
        )

        val logInResult = makeSafeApiCall {
            authService.logIn(CredentialsRemote(email, password))
        }.handleApiErrors()

        jwtManager.saveTokensFromResponse(logInResult)
    }


    override suspend fun signInUser(email: String?, password: String?): Boolean {

        if (email.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw AuthException.InvalidEmailException("Invalid email address")
        }

        if (password.isNullOrEmpty()) {
            throw AuthException.NoEmptyPasswordException("Password must not be empty")
        }

        val response = makeSafeApiCall {
            authService.logIn(CredentialsRemote(email, password))
        }.handleApiErrors(
            hashMapOf(
                HttpURLConnection.HTTP_NOT_FOUND to AuthException.NoSuchEmailException("User with such email does not exist"),
                HttpURLConnection.HTTP_UNAUTHORIZED to AuthException.WrongPasswordException("Wrong password")
            )
        )
        jwtManager.saveTokensFromResponse(response)
        response.id?.let {
            userDataStore.saveUserId(it)
        }
        return true
    }

    override suspend fun sendNewPassword(email: String) {
        makeSafeApiCall {
            authService.sendPassword(SendNewPasswordOnEmailRequestDto(email))
        }.handleApiErrors(
            hashMapOf(
                404 to AuthException.NoSuchEmailException("User with email $email doesn't exist")
            )
        )
    }
}
