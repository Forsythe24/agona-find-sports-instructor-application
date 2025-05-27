package com.solopov.feature_user_profile_impl.presentation.edit_profile

import androidx.lifecycle.viewModelScope
import com.solopov.com.solopov.feature_user_profile_impl.R
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.ApiError
import com.solopov.common.data.network.getMessage
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_user_profile_api.domain.usecase.UpdateUserInfoUseCase
import com.solopov.feature_user_profile_api.domain.usecase.UpdateUserPasswordUseCase
import com.solopov.feature_user_profile_api.domain.usecase.VerifyCredentialsUseCase
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.data.mappers.UserMappers
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val mappers: UserMappers,
    private val router: UserProfileRouter,
    private val userDataValidator: UserDataValidator,
    private val resourceManager: ResourceManager,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val updateUserPasswordUseCase: UpdateUserPasswordUseCase,
    private val verifyCredentialsUseCase: VerifyCredentialsUseCase
) : BaseViewModel() {

    private val _editProfileState = MutableStateFlow<UserProfile?>(null)
    val editProfileState: StateFlow<UserProfile?> = _editProfileState.asStateFlow()

    private val _saveLoadingState = MutableStateFlow(false)
    val saveLoadingState: StateFlow<Boolean> = _saveLoadingState.asStateFlow()

    private val _dialogLoadingState = MutableStateFlow(false)
    val dialogLoadingState: StateFlow<Boolean> = _dialogLoadingState.asStateFlow()

    private val _passwordErrorTextState = MutableStateFlow<String?>(null)
    val passwordErrorTextState: StateFlow<String?> = _passwordErrorTextState.asStateFlow()

    fun updateUser(
        userProfile: UserProfile,
        onUserUpdated: () -> Unit,
    ) {
        _saveLoadingState.value = true
        viewModelScope.launch {
            runCatching {
                updateUserInfoUseCase(mappers.mapUserProfileToUser(userProfile))
            }.onSuccess {
                onUserUpdated()
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }.also {
                _saveLoadingState.value = false
            }
        }
    }

    fun updateUserPassword(password: String, onPasswordUpdated: () -> Unit) {
        _dialogLoadingState.value = true
        viewModelScope.launch {
            runCatching {
                updateUserPasswordUseCase(password)
            }.onSuccess {
                onPasswordUpdated()
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }.also {
                _dialogLoadingState.value = false
            }
        }
    }

    fun verifyCredentials(
        allegedPassword: String,
        onCorrectPasswordCallback: () -> Unit,
        onWrongPasswordCallback: () -> Unit,
    ) {
        _dialogLoadingState.value = true
        viewModelScope.launch {
            runCatching {
                verifyCredentialsUseCase(allegedPassword)
            }.onSuccess { areVerified ->
                if (areVerified) {
                    onCorrectPasswordCallback()
                } else {
                    onWrongPasswordCallback()
                }
            }.onFailure {
                when (it) {
                    is ApiError.FailedAuthorizationException -> _passwordErrorTextState.value = resourceManager.getString(R.string.wrong_password)
                    else -> showMessage(it.getMessage(resourceManager))
                }
            }.also {
                _dialogLoadingState.value = false
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
        _editProfileState.value = user
    }
}
