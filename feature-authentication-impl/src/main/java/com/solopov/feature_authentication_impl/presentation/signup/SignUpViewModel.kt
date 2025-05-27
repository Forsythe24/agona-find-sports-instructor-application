package com.solopov.feature_authentication_impl.presentation.signup

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.ApiError
import com.solopov.common.data.network.getMessage
import com.solopov.common.utils.UserDataValidator
import com.solopov.feature_authentication_api.domain.usecase.RegisterUserUseCase
import com.solopov.feature_authentication_impl.AuthRouter
import com.solopov.feature_authentication_impl.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val router: AuthRouter,
    private val validator: UserDataValidator,
    private val resourceManager: ResourceManager,
    private val registerUserUseCase: RegisterUserUseCase
) : BaseViewModel() {

    private val _emailErrorTextState = MutableStateFlow<String?>(null)
    val emailErrorTextState: StateFlow<String?> = _emailErrorTextState.asStateFlow()

    fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ) {
        setLoadingState(true)
        viewModelScope.launch {
            runCatching {
                registerUserUseCase(
                    email,
                    password,
                    name,
                    age,
                    gender,
                )
            }.onSuccess {
                router.goFromSignUpToInstructors()
            }.onFailure {
                when (it) {
                    is ApiError.ConflictException -> _emailErrorTextState.value =
                        resourceManager.getString(R.string.email_already_in_use_message)

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

    fun validatePassword(password: String): String? {
        return validator.validatePassword(password)
    }

    fun validateAge(age: String): String? {
        return validator.validateAge(age)
    }

    fun validateName(name: String): String? {
        return validator.validateName(name)
    }

    fun goBack() {
        router.goBack()
    }
}
