package com.solopov.feature_authentication_impl.presentation.login

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.ApiError
import com.solopov.common.data.network.getMessage
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_authentication_api.domain.usecase.SignInUserUseCase
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val router: AuthRouter,
    private val validator: UserDataValidator,
    private val resourceManager: ResourceManager,
    private val signInUserUseCase: SignInUserUseCase
) : BaseViewModel() {

    private val _errorMessageChannel = Channel<String>()
    val errorMessageChannel = _errorMessageChannel.receiveAsFlow()

    private val _authenticationResultFlow = MutableStateFlow(false)
    val authenticationResultFlow: StateFlow<Boolean>
        get() = _authenticationResultFlow

    private val _emailErrorTextFlow = MutableStateFlow<String?>(null)
    val emailErrorTextFlow: StateFlow<String?>
        get() = _emailErrorTextFlow

    private val _passwordErrorTextFlow = MutableStateFlow<String?>(null)
    val passwordErrorTextFlow: StateFlow<String?>
        get() = _passwordErrorTextFlow

    fun signIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            runCatching {
                signInUserUseCase(
                    email,
                    password,
                )
            }.onSuccess {
                _authenticationResultFlow.value = it
            }.onFailure {
                when (it) {
                    is ApiError.NotFoundException -> _emailErrorTextFlow.value = resourceManager.getString(R.string.no_such_email_message)
                    is ApiError.FailedAuthorizationException -> _passwordErrorTextFlow.value = resourceManager.getString(R.string.wrong_password_message)
                    else -> _errorMessageChannel.send(it.getMessage(resourceManager))
                }
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
