package com.solopov.feature_authentication_impl.presentation.login

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.ApiError
import com.solopov.common.data.network.getMessage
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_authentication_api.domain.AuthInteractor
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val router: AuthRouter,
    private val validator: UserDataValidator,
    private val resourceManager: ResourceManager,
) : BaseViewModel() {

    private val _errorMessageChannel = Channel<String>()
    val errorMessageChannel = _errorMessageChannel.receiveAsFlow()

    private val _authenticationResultFlow = MutableStateFlow(false)
    val authenticationResultFlow: StateFlow<Boolean>
        get() = _authenticationResultFlow

    fun signIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            runCatching {
                interactor.signInUser(
                    email,
                    password,
                )
            }.onSuccess {
                _authenticationResultFlow.value = it
            }.onFailure {
                _errorMessageChannel.send(
                    when (it) {
                        is ApiError.FailedAuthorizationException -> resourceManager.getString(R.string.wrong_password_message)
                        else -> it.getMessage(resourceManager)
                    }
                )
            }
        }
    }

    fun validateEmail(email: String): String? {
        return validator.validateEmail(email)
    }

    fun goFromLogInToUserProfile() {
        router.goFromLogInToUserProfile()
    }

    fun goToSignUp() {
        router.goToSignUp()
    }

    fun goToPasswordRecovery() {
        router.goToPasswordRecovery()
    }

    override fun onCleared() {
        _errorMessageChannel.close()
        super.onCleared()
    }
}
