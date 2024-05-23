package com.solopov.feature_user_profile_impl.presentation.edit_profile

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_user_profile_api.domain.interfaces.UserProfileInteractor
import com.solopov.feature_user_profile_impl.UserProfileRouter
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
    private val mappers: UserMappers,
    private val router: UserProfileRouter,
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

    val errorsChannel = Channel<Throwable>()

    fun updateUser(
        userProfile: UserProfile,
        onUserUpdated: () -> Unit
    ) {
        _saveBtnProgressBarFlow.value = true
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.updateUser(mappers.mapUserProfileToUser(userProfile))
            }.onSuccess {
                onUserUpdated()
                _saveBtnProgressBarFlow.value = false
            }.onFailure {
                errorsChannel.send(it)
                _saveBtnProgressBarFlow.value = false
            }
        }
    }

    fun updateUserPassword(password: String, onPasswordUpdated: () -> Unit) {
        _dialogBtnProgressBarFlow.value = true
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.updateUserPassword(password)
            }.onSuccess {
                onPasswordUpdated()
                _dialogBtnProgressBarFlow.value = false
            }.onFailure {
                errorsChannel.send(it)
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
            runCatching(exceptionHandlerDelegate) {
                interactor.verifyCredentials(allegedPassword)
            }.onSuccess { areVerified ->
                if (areVerified) {
                    onCorrectPasswordCallback()
                } else {
                    onWrongPasswordCallback()
                }
                _dialogBtnProgressBarFlow.value = false
            }.onFailure {
                errorsChannel.send(it)
                _dialogBtnProgressBarFlow.value = false
            }
        }
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
        errorsChannel.close()
        super.onCleared()
    }
}
