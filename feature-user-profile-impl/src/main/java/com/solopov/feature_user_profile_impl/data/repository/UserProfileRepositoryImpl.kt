package com.solopov.feature_user_profile_impl.data.repository

import androidx.core.net.toUri
import com.google.firebase.storage.FirebaseStorage
import com.solopov.common.data.network.ApiError
import com.solopov.common.data.network.api.RefreshTokenService
import com.solopov.common.data.network.api.UserApiService
import com.solopov.common.data.network.exceptions.FirebaseException
import com.solopov.common.data.network.jwt.JwtManager
import com.solopov.common.data.network.jwt.saveTokensFromResponse
import com.solopov.common.data.network.makeSafeApiCall
import com.solopov.common.data.network.model.CredentialsRemote
import com.solopov.common.data.network.model.RefreshJwtRequestDto
import com.solopov.common.data.network.utils.NetworkStateProvider
import com.solopov.common.data.storage.UserDataStore
import com.solopov.common.utils.DateFormatter
import com.solopov.feature_user_profile_api.domain.UserProfileRepository
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import kotlinx.coroutines.tasks.await
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
    private val networkStateProvider: NetworkStateProvider,
) : UserProfileRepository {


    override suspend fun getUserByUid(uid: String): User {
        return userMappers.mapUserRemoteToUser(
            makeSafeApiCall(networkStateProvider) {
                apiService.getUser(uid)
            }
        )
    }

    override suspend fun getCurrentUser(): User {
        return userMappers.mapUserRemoteToUser(
            makeSafeApiCall(networkStateProvider) {
                apiService.getCurrentUser()
            }
        )
    }

    override suspend fun updateUser(user: User) {
        makeSafeApiCall(networkStateProvider) {
            apiService.updateUser(userMappers.mapUserToUserRemote(user))
        }
    }

    override suspend fun updateUserPassword(password: String) {
        makeSafeApiCall(networkStateProvider) {
            apiService.updatePassword(CredentialsRemote("", password))
        }

        val response = makeSafeApiCall(networkStateProvider) {
            refreshTokenService.refreshToken(RefreshJwtRequestDto(jwtManager.getRefreshJwt()))
        }

        jwtManager.saveTokensFromResponse(response)
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
        return makeSafeApiCall(networkStateProvider) {
            apiService.verifyCredentials(
                CredentialsRemote(
                    "",
                    password
                )
            )
        }
    }

    override suspend fun deleteProfile(): Boolean {
        return makeSafeApiCall(networkStateProvider) {
            apiService.deleteUser()
        }
    }

    override suspend fun logOut() {
        val token = jwtManager.getRefreshJwt()
        if (token == null) {
            throw ApiError.FailedAuthorizationException("Failed to log out because refresh token is not valid")
        } else {
            val response = makeSafeApiCall(networkStateProvider) {
                apiService.logOut(RefreshJwtRequestDto(token))
            }
            jwtManager.saveTokensFromResponse(response)
            userDataStore.clearUserId()
        }
    }

    companion object {
        private const val FILE_PATH_TEMPLATE = "images/%s"
    }
}

