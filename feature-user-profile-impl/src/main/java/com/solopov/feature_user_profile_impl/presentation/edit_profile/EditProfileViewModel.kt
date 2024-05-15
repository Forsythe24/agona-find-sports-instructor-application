package com.solopov.feature_user_profile_impl.presentation.edit_profile

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileInteractor
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val interactor: UserProfileInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val mappers: UserMappers
) : BaseViewModel() {

    private val _editProfileFlow = MutableStateFlow<UserProfile?>(null)
    val editProfileFlow: StateFlow<UserProfile?>
        get() = _editProfileFlow

    private val _progressBarFlow = MutableStateFlow(false)
    val progressBarFlow: StateFlow<Boolean>
        get() = _progressBarFlow

    val errorsChannel = Channel<Throwable>()

    fun updateUser(
        userProfile: UserProfile,
        onUserUpdated: () -> Unit
    ) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.updateUser(mappers.mapUserProfileToUser(userProfile))
            }.onSuccess {
                onUserUpdated()
                _progressBarFlow.value = false
            }.onFailure {
                errorsChannel.send(it)
                _progressBarFlow.value = false
            }
        }
    }

    fun updateUserPassword(password: String, onPasswordUpdated: () -> Unit) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.updateUserPassword(password)
            }.onSuccess {
                onPasswordUpdated()
                _progressBarFlow.value = false
            }.onFailure {
                errorsChannel.send(it)
                _progressBarFlow.value = false
            }
        }
    }

    fun verifyCredentials(
        allegedPassword: String,
        onCorrectPasswordCallback: () -> Unit,
        onWrongPasswordCallback: () -> Unit,
    ){
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.verifyCredentials(allegedPassword)
            }.onSuccess { areVerified ->
                if (areVerified) {
                    onCorrectPasswordCallback()
                } else {
                    onWrongPasswordCallback()
                }
                _progressBarFlow.value = false
            }.onFailure {
                errorsChannel.send(it)
                _progressBarFlow.value = false
            }
        }
    }

    fun setCurrentUser(user: UserProfile) {
        _editProfileFlow.value = user
    }

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }
}
