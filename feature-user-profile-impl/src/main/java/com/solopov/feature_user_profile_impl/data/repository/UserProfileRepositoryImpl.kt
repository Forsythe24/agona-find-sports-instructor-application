package com.solopov.feature_user_profile_impl.data.repository

import androidx.core.net.toUri
import com.google.firebase.storage.FirebaseStorage
import com.solopov.common.data.network.api.RefreshTokenService
import com.solopov.common.data.network.api.UserApiService
import com.solopov.common.data.network.exceptions.AuthException
import com.solopov.common.data.network.exceptions.FirebaseException
import com.solopov.common.data.network.exceptions.HttpException
import com.solopov.common.data.network.exceptions.UserException
import com.solopov.common.data.network.jwt.JwtManager
import com.solopov.common.data.network.jwt.saveTokensFromResponse
import com.solopov.common.data.network.model.CredentialsRemote
import com.solopov.common.data.network.model.RefreshJwtRequestDto
import com.solopov.common.data.network.utils.handleApiErrors
import com.solopov.common.data.network.utils.makeSafeApiCall
import com.solopov.common.data.storage.UserDataStore
import com.solopov.common.utils.DateFormatter
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileRepository
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import kotlinx.coroutines.tasks.await
import java.net.HttpURLConnection
import java.util.Date
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val userMappers: UserMappers,
    private val apiService: UserApiService,
    private val refreshTokenService: RefreshTokenService,
    private val jwtManager: JwtManager,
    private val storage: FirebaseStorage,
    private val dateFormatter: DateFormatter,
    private val userDataStore: UserDataStore,
) : UserProfileRepository {


    override suspend fun getUserByUid(uid: String): User {
        return userMappers.mapUserRemoteToUser(
            makeSafeApiCall {
                apiService.getUser(uid)
            }.handleApiErrors(
                hashMapOf(
                    HttpURLConnection.HTTP_UNAUTHORIZED to HttpException.UnauthorizedException(
                        "Failed to download user data by uid due to lack of authority: $uid",
                    ),
                    HttpURLConnection.HTTP_NOT_FOUND to UserException.UserNotFoundException("User ($uid) was not found")
                )
            )
        )
    }

    override suspend fun getCurrentUser(): User {
        return userMappers.mapUserRemoteToUser(
            makeSafeApiCall {
                apiService.getCurrentUser()
            }.handleApiErrors(
                hashMapOf(
                    HttpURLConnection.HTTP_UNAUTHORIZED to HttpException.UnauthorizedException(
                        "Failed to download current user data due to their lack of authority",
                    ),
                    HttpURLConnection.HTTP_NOT_FOUND to UserException.UserNotFoundException("Current user was not found")
                )
            )
        )
    }

    override suspend fun updateUser(user: User) {
        makeSafeApiCall {
            apiService.updateUser(userMappers.mapUserToUserRemote(user))
        }.handleApiErrors(
            hashMapOf(
                HttpURLConnection.HTTP_UNAUTHORIZED to HttpException.UnauthorizedException(
                    "Failed to update current user data due to their lack of authority",
                )
            )
        )
    }

    override suspend fun updateUserPassword(password: String) {
        makeSafeApiCall {
            apiService.updatePassword(CredentialsRemote("", password))
        }.handleApiErrors()

        val networkResponse =
            refreshTokenService.refreshToken(RefreshJwtRequestDto(jwtManager.getRefreshJwt()))

        val body = networkResponse.body()
        if (body == null) {
            throw AuthException.FailedToAuthorizeException("The authorization process failed: response body is null")
        } else {
            jwtManager.saveTokensFromResponse(body)
        }
    }

    override suspend fun uploadProfileImage(imageUri: String): String {
        val filename = dateFormatter.formatDateTo_yyyyMMddHHmmss_DateFormat(Date())
        val location = FILE_PATH_TEMPLATE.format(filename)

        val ref = storage.getReference(location)

        try {
            ref.putFile(imageUri.toUri()).await()
            return ref.downloadUrl.await().toString()
        } catch (ex: Exception) {
            throw FirebaseException.FileUploadingException("Failed to upload the file to the remote storage")
        }
    }

    override suspend fun verifyCredentials(password: String): Boolean {
        return makeSafeApiCall {
            apiService.verifyCredentials(
                CredentialsRemote(
                    "",
                    password
                )
            )
        }.handleApiErrors()
    }

    override suspend fun deleteProfile(): Boolean {
        return makeSafeApiCall {
            apiService.deleteUser()
        }.handleApiErrors()
    }

    override suspend fun logOut() {
        val token = jwtManager.getRefreshJwt()
        if (token == null) {
            throw HttpException.UnauthorizedException("Failed to log out because refresh token is not valid")
        } else {
            val response = makeSafeApiCall {
                apiService.logOut(RefreshJwtRequestDto(token))
            }.handleApiErrors()
            jwtManager.saveTokensFromResponse(response)
            userDataStore.clearUserId()
        }
    }

    companion object {
        private const val FILE_PATH_TEMPLATE = "images/%s"
    }
}

