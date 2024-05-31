package com.solopov.common.data.remote.dao

import android.util.Patterns
import androidx.core.net.toUri
import com.google.firebase.storage.FirebaseStorage
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.remote.AuthService
import com.solopov.common.data.remote.RefreshTokenService
import com.solopov.common.data.remote.SportApi
import com.solopov.common.data.remote.exceptions.AuthException
import com.solopov.common.data.remote.exceptions.FirebaseException
import com.solopov.common.data.remote.exceptions.HttpException
import com.solopov.common.data.remote.exceptions.UserException
import com.solopov.common.data.remote.jwt.JwtManager
import com.solopov.common.data.remote.model.AuthNetworkResponse
import com.solopov.common.data.remote.model.CredentialsRemote
import com.solopov.common.data.remote.model.RefreshJwtRequestDto
import com.solopov.common.data.remote.model.SendNewPasswordOnEmailRequestDto
import com.solopov.common.data.remote.model.UserRemote
import com.solopov.common.data.remote.model.UserSignUpRemote
import com.solopov.common.utils.ExceptionHandlerDelegate
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class UserRemoteDao @Inject constructor(
    private val resManager: ResourceManager,
    private val storage: FirebaseStorage,
    private val api: SportApi,
    private val authService: AuthService,
    private val refreshTokenService: RefreshTokenService,
    private val jwtManager: JwtManager,
) {

    suspend fun getInstructorsBySportId(sportId: Int): List<UserRemote> {
        val response = api.getInstructorsBySportId(sportId)
        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
            401 -> throw HttpException.UnauthorizedException(resManager.getString(R.string.unauthorized_exception))
            else -> return response.body()!!
        }
    }

    suspend fun sendPassword(email: String) {
        val response = authService.sendPassword(SendNewPasswordOnEmailRequestDto(email))
        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
            404 -> throw AuthException.NoSuchEmailException(
                resManager.getString(R.string.email_not_found_exception).format(email)
            )
        }
    }

    suspend fun verifyCredentials(password: String): Boolean {
        val response = api.verifyCredentials(
            CredentialsRemote(
                "",
                password
            )
        )

        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))

            else -> return response.body()!!
        }
    }


    suspend fun uploadProfileImage(imageUri: String): String {
        val formatter = SimpleDateFormat(
            resManager.getString(R.string.profile_image_upload_date_format),
            Locale.getDefault()
        )
        val now = Date()
        val filename = formatter.format(now)
        val location =
            resManager.getString(R.string.profile_image_file_path_template).format(filename)


        val ref = storage.getReference(location)

        try {
            ref.putFile(imageUri.toUri()).await()
            return ref.downloadUrl.await().toString()
        } catch (ex: Exception) {
            throw FirebaseException.FileUploadingException(resManager.getString(R.string.file_uploading_exception))
        }
    }

    suspend fun getUserByUid(uid: String): UserRemote {
        val response = api.getUser(uid)

        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
            404 -> throw UserException.UserNotFound(resManager.getString(R.string.user_not_found_exception))
            else -> return response.body()!!
        }
    }

    suspend fun getCurrentUser(): UserRemote {
        val response = api.getCurrentUser()

        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
            404 -> throw UserException.UserNotFound(resManager.getString(R.string.user_not_found_exception))
            else -> return response.body()!!
        }
    }

    suspend fun deleteProfile(): Boolean {
        val response = api.deleteUser()
        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
            else -> return response.body()!!
        }
    }

    suspend fun getCurrentUserId(): String {
        return getCurrentUser().id
    }

    suspend fun updateUser(user: UserRemote) {
        val response = api.updateUser(user)

        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
            404 -> throw UserException.UserDataUpdateFailedException(resManager.getString(R.string.user_data_update_failed_exception))
        }
    }

    suspend fun updateUserPassword(password: String) {

        val response = api.updatePassword(CredentialsRemote("", password))

        val networkResponse =
            refreshTokenService.refreshToken(RefreshJwtRequestDto(jwtManager.getRefreshJwt()))

        saveTokens(networkResponse)

        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
        }
    }


    suspend fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ) {
        val addResponse = addUserToRemoteStorage(
            email,
            password,
            name,
            age,
            gender
        )

        when (addResponse.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
            409 -> throw AuthException.EmailAlreadyInUseException(resManager.getString(R.string.email_already_in_use_exception))
        }

        val logInResponse = authService.logIn(CredentialsRemote(email, password))

        when (logInResponse.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
            404 -> throw UserException.UserNotCreatedException(resManager.getString(R.string.couldn_t_create_an_account_try_again))
            else -> saveTokens(logInResponse)
        }
    }

    suspend fun signInUser(email: String?, password: String?): Boolean {

        if (email.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw AuthException.InvalidEmailException(resManager.getString(R.string.invalid_email_exception))
        }

        if (password.isNullOrEmpty()) {
            throw AuthException.NoEmptyPasswordException(resManager.getString(R.string.password_must_not_be_empty))
        }

        val response = authService.logIn(CredentialsRemote(email, password))

        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
            404 -> throw AuthException.NoSuchEmailException(
                resManager.getString(R.string.email_not_found_exception).format(email)
            )

            401 -> throw AuthException.WrongPasswordException(resManager.getString(R.string.wrong_password))
            else -> {
                saveTokens(response)
                return true
            }
        }
    }

    private suspend fun addUserToRemoteStorage(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ): Response<UserRemote> {
        val user = UserSignUpRemote(
            email, password, name, age, gender,
        )
        return authService.createUser(user)
    }

    private suspend fun saveTokens(response: Response<AuthNetworkResponse>) {
        response.body()?.let { body ->
            jwtManager.saveAccessJwt(body.accessToken)
            jwtManager.saveRefreshJwt(body.refreshToken)
        }
    }
}
