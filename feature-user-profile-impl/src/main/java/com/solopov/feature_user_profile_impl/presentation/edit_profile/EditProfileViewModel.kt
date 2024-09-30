package com.solopov.feature_user_profile_impl.presentation.edit_profile

import androidx.lifecycle.viewModelScope
import com.solopov.com.solopov.feature_user_profile_impl.R
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.ApiError
import com.solopov.common.data.network.getMessage
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_user_profile_api.domain.UserProfileInteractor
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val interactor: UserProfileInteractor,
    private val mappers: UserMappers,
    private val router: UserProfileRouter,
    private val userDataValidator: UserDataValidator,
    private val resourceManager: ResourceManager,
) : BaseViewModel() {

    private val _editProfileFlow = MutableStateFlow<UserProfile?>(null)
    val editProfileFlow: StateFlow<UserProfile?>
        get() = _editProfileFlow

    private val _saveBtnProgressBarFlow = MutableStateFlow(false)
    val saveBtnProgressBarFlow: StateFlow<Boolean>
        get() = _saveBtnProgressBarFlow

    private val _dialogBtnProgressBarFlow = MutableStateFlow(false)
    val dialogBtnProgressBarFlow: StateFlow<Boolean>
        get() = _dialogBtnProgressBarFlow

    private val _errorMessageChannel = Channel<String>()
    val errorMessageChannel = _errorMessageChannel.receiveAsFlow()

    private val _passwordErrorTextFlow = MutableStateFlow<String?>(null)
    val passwordErrorTextFlow: StateFlow<String?>
        get() = _passwordErrorTextFlow

    fun updateUser(
        userProfile: UserProfile,
        onUserUpdated: () -> Unit,
    ) {
        _saveBtnProgressBarFlow.value = true
        viewModelScope.launch {
            runCatching {
                interactor.updateUser(mappers.mapUserProfileToUser(userProfile))
            }.onSuccess {
                onUserUpdated()
                _saveBtnProgressBarFlow.value = false
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
                _saveBtnProgressBarFlow.value = false
            }
        }
    }

    fun updateUserPassword(password: String, onPasswordUpdated: () -> Unit) {
        _dialogBtnProgressBarFlow.value = true
        viewModelScope.launch {
            runCatching {
                interactor.updateUserPassword(password)
            }.onSuccess {
                onPasswordUpdated()
                _dialogBtnProgressBarFlow.value = false
            }.onFailure {
                _errorMessageChannel.send(it.getMessage(resourceManager))
                _dialogBtnProgressBarFlow.value = false
            }
        }
    }

    fun verifyCredentials(
        allegedPassword: String,
        onCorrectPasswordCallback: () -> Unit,
        onWrongPasswordCallback: () -> Unit,
    ) {
        _dialogBtnProgressBarFlow.value = true
        viewModelScope.launch {
            runCatching {
                interactor.verifyCredentials(allegedPassword)
            }.onSuccess { areVerified ->
                if (areVerified) {
                    onCorrectPasswordCallback()
                } else {
                    onWrongPasswordCallback()
                }
                _dialogBtnProgressBarFlow.value = false
            }.onFailure {
                when (it) {
                    is ApiError.FailedAuthorizationException -> _passwordErrorTextFlow.value = resourceManager.getString(R.string.wrong_password)
                    else -> _errorMessageChannel.send(it.getMessage(resourceManager))
                }
                _dialogBtnProgressBarFlow.value = false
            }
        }
    }

    fun validateAge(text: String): String? {
        return userDataValidator.validateAge(text)
    }

    fun validateName(text: String): String? {
        return userDataValidator.validateName(text)
    }

    fun validatePassword(text: String): String? {
        return userDataValidator.validatePassword(text)
    }


    fun goBack() {
        router.goBack()
    }

    fun goToInstructApplication(user: UserProfile) {
        router.goToInstructApplication(user)
    }

    fun setCurrentUser(user: UserProfile) {
        _editProfileFlow.value = user
    }

    override fun onCleared() {
        _errorMessageChannel.close()
        super.onCleared()
    }
}
