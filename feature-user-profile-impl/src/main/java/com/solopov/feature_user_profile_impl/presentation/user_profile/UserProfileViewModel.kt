package com.solopov.feature_user_profile_impl.presentation.user_profile

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileInteractor
import com.solopov.common.base.BaseViewModel
import com.solopov.common.model.UserCommon
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val interactor: UserProfileInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val userMappers: UserMappers
): BaseViewModel() {

    private val _userProfileFlow = MutableStateFlow<UserProfile?>(null)
    val userProfileFlow: StateFlow<UserProfile?>
        get() = _userProfileFlow

    val errorsChannel = Channel<Throwable>()

    fun getUserByUid(uid: String) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getUserByUid(uid)
            }.onSuccess {
                _userProfileFlow.value = userMappers.mapUserToUserProfile(it)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun setCurrentUser() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getCurrentUser()
            }.onSuccess {
                _userProfileFlow.value = userMappers.mapUserToUserProfile(it)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    private fun updateUser(
        userProfile: UserProfile
    ) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.updateUser(userMappers.mapUserProfileToUser(userProfile))
            }.onSuccess {
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun setUser(user: UserCommon) {
        _userProfileFlow.value = userMappers.mapUserCommonToUserProfile(user)
    }

    fun updateProfileImage(imageUri: String) {
        _userProfileFlow.value?.let {
            it.photo = imageUri
            updateUser(it)
        }
    }

    fun uploadProfileImage(imageUri: Uri) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.uploadProfileImage(imageUri.toString())
            }.onSuccess {
                updateProfileImage(it)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }
}
