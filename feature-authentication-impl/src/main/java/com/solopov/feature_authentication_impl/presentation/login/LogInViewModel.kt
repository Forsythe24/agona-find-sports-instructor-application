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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogInViewModel @Inject constructor(
    private val router: AuthRouter,
    private val validator: UserDataValidator,
    private val resourceManager: ResourceManager,
    private val signInUserUseCase: SignInUserUseCase
) : BaseViewModel() {

    private val _emailErrorTextState = MutableStateFlow<String?>(null)
    val emailErrorTextState: StateFlow<String?> = _emailErrorTextState.asStateFlow()

    private val _passwordErrorTextState = MutableStateFlow<String?>(null)
    val passwordErrorTextState: StateFlow<String?> = _passwordErrorTextState.asStateFlow()

    fun signIn(
        email: String,
        password: String,
    ) {
        setLoadingState(true)
        viewModelScope.launch {
            runCatching {
                signInUserUseCase(
                    email,
                    password,
                )
            }.onSuccess { isAuthenticated ->
                if (isAuthenticated) {
                    goFromLogInToUserProfile()
                }
            }.onFailure {
                when (it) {
                    is ApiError.NotFoundException -> _emailErrorTextState.value = resourceManager.getString(R.string.no_such_email_message)
                    is ApiError.FailedAuthorizationException -> _passwordErrorTextState.value = resourceManager.getString(R.string.wrong_password_message)
                    else -> showMessage(it.getMessage(resourceManager))
                }
            }.also {
                setLoadingState(false)
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
}
