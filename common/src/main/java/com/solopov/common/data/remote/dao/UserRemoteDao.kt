package com.solopov.common.data.remote.dao

import android.util.Patterns
import androidx.core.net.toUri
import com.google.firebase.storage.FirebaseStorage
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.remote.exceptions.AuthenticationException
import com.solopov.common.data.remote.exceptions.CredentialsVerificationFailedException
import com.solopov.common.data.remote.exceptions.FileUploadingException
import com.solopov.common.data.remote.exceptions.NoInstructorsFoundException
import com.solopov.common.data.remote.exceptions.UserDataUpdateFailedException
import com.solopov.common.data.remote.exceptions.UserDoesNotExistException
import com.solopov.common.data.remote.exceptions.UserNotCreatedException
import com.solopov.common.data.remote.jwt.JwtTokenManager
import com.solopov.common.data.remote.model.AuthNetworkResponse
import com.solopov.common.data.remote.model.CredentialsRemote
import com.solopov.common.data.remote.model.UserRemote
import com.solopov.common.data.remote.model.UserSignUpRemote
import com.solopov.common.data.remote.AuthService
import com.solopov.common.data.remote.RefreshTokenService
import com.solopov.common.data.remote.SportApi
import com.solopov.common.data.remote.exceptions.HttpException
import com.solopov.common.data.remote.model.RefreshJwtRequestDto
import com.solopov.common.data.remote.model.SendNewPasswordOnEmailRequestDto
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class UserRemoteDao @Inject constructor(
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val resManager: ResourceManager,
    private val storage: FirebaseStorage,
    private val api: SportApi,
    private val authService: AuthService,
    private val refreshTokenService: RefreshTokenService,
    private val jwtTokenManager: JwtTokenManager,
) {

    suspend fun getInstructorsBySportId(sportId: Int): List<UserRemote> {
        runCatching(exceptionHandlerDelegate) {
            api.getInstructorsBySportId(sportId)
        }.onSuccess {
            return it
        }.onFailure {
            throw NoInstructorsFoundException(resManager.getString(R.string.no_instructors_found_exception))
        }
        throw NoInstructorsFoundException(resManager.getString(R.string.no_instructors_found_exception))
    }

    suspend fun sendPassword(email: String) {
        withContext(Dispatchers.IO) {
            val response = authService.sendPassword(SendNewPasswordOnEmailRequestDto(email))

//            when (response.code()) {
//                500 -> throw HttpException.ServerNotResponding(resManager.getString(R.string.server_not_responding))
//                403 -> throw HttpException.ServerNotResponding(resManager.getString(R.string.server_not_responding))
//                else -> {}
//            }
        }
    }

    suspend fun verifyCredentials(password: String): Boolean {
        runCatching(exceptionHandlerDelegate) {
            api.verifyCredentials(
                CredentialsRemote(
                    "",
                    password
                )
            )
        }.onSuccess {
            return it
        }.onFailure {
            throw CredentialsVerificationFailedException(resManager.getString(R.string.failed_to_verify_your_credentials_check_your_password))
        }
        throw CredentialsVerificationFailedException(resManager.getString(R.string.failed_to_verify_your_credentials_check_your_password))
    }


    suspend fun uploadProfileImage(imageUri: String): String {
        val formatter = SimpleDateFormat(
            resManager.getString(R.string.profile_image_upload_date_format),
            Locale.getDefault()
        )
        val now = Date()
        val filename = formatter.format(now)
        val location =
            resManager.getString(R.string.profile_image_upload_date_format).format(filename)


        val ref = storage.getReference(location)
        runCatching(exceptionHandlerDelegate) {
            ref.putFile(imageUri.toUri()).await()
        }.onSuccess {
            return ref.downloadUrl.await().toString()

        }.onFailure {
            throw FileUploadingException(resManager.getString(R.string.file_uploading_exception))
        }
        throw FileUploadingException(resManager.getString(R.string.file_uploading_exception))
    }

    suspend fun getUserByUid(uid: String): UserRemote {

        runCatching(exceptionHandlerDelegate) {
            api.getUser(uid)
        }.onSuccess {
            return it
        }.onFailure {
            throw UserDoesNotExistException(resManager.getString(R.string.this_user_does_not_exist_exception))
        }
        throw UserDoesNotExistException(resManager.getString(R.string.this_user_does_not_exist_exception))

    }

    suspend fun getCurrentUser(): UserRemote {
        return api.getCurrentUser()
    }

    suspend fun updateUser(user: UserRemote) {
        runCatching(exceptionHandlerDelegate) {
            api.updateUser(user)
        }.onSuccess {
            return
        }.onFailure {
            throw UserDataUpdateFailedException(resManager.getString(R.string.user_data_update_failed_exception))
        }
    }

    suspend fun updateUserPassword(password: String) {
        withContext(Dispatchers.IO) {
            try {
                api.updatePassword(CredentialsRemote("", password))

                val networkResponse = refreshTokenService.refreshToken(RefreshJwtRequestDto(jwtTokenManager.getRefreshJwt()))
                saveTokens(networkResponse)
            } catch (ex: Exception) {
                throw UserDataUpdateFailedException(resManager.getString(R.string.failed_to_update_your_password))
            }
        }
    }


    suspend fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ) {
        runCatching(exceptionHandlerDelegate) {
            addUserToRemoteStorage(
                email,
                password,
                name,
                age,
                gender
            )
        }.onSuccess {

            val response = authService.login(CredentialsRemote(email, password))

            saveTokens(response)

            return
        }.onFailure {
            throw UserNotCreatedException(resManager.getString(R.string.couldn_t_create_an_account_try_again))
        }
        throw UserNotCreatedException(resManager.getString(R.string.couldn_t_create_an_account_try_again))
    }

    private suspend fun addUserToRemoteStorage(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ): UserRemote {
        val user = UserSignUpRemote(
            email, password, name, age, gender,
        )
        return withContext(Dispatchers.IO) {
            authService.createUser(user)
        }
    }

    suspend fun signInUser(email: String?, password: String?): Boolean {

        if (email.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw AuthenticationException.InvalidEmailException(resManager.getString(R.string.invalid_email_exception))
        }

        if (password.isNullOrEmpty()) {
            throw AuthenticationException.NoEmptyPasswordException(resManager.getString(R.string.password_must_not_be_empty))
        }

        runCatching(exceptionHandlerDelegate) {
            authService.login(CredentialsRemote(email, password))
        }.onSuccess { response ->
            if (response.body() == null) {
                throw AuthenticationException.WrongEmailOrPasswordException(resManager.getString(R.string.authentication_failed))
            }
            saveTokens(response)
            runCatching {
                getCurrentUser()
            }.onSuccess {
                return true
            }.onFailure {
                throw UserDoesNotExistException(resManager.getString(R.string.this_user_does_not_exist_exception))
            }
        }.onFailure {
            throw AuthenticationException.WrongEmailOrPasswordException(resManager.getString(R.string.authentication_failed))
        }

        throw AuthenticationException.WrongEmailOrPasswordException(resManager.getString(R.string.authentication_failed))
    }

    private suspend fun saveTokens(response: Response<AuthNetworkResponse>) {
        response.body()?.let { body ->
            jwtTokenManager.saveAccessJwt(body.accessToken)
            jwtTokenManager.saveRefreshJwt(body.refreshToken)
        }
    }
}
