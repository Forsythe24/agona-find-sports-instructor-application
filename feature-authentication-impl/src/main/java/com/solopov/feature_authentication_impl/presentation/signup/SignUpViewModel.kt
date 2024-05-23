package com.solopov.feature_authentication_impl.presentation.signup

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.UserDataValidator
import com.solopov.common.utils.runCatching
import com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor
import com.solopov.feature_authentication_impl.AuthRouter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val router: AuthRouter,
    private val validator: UserDataValidator,
) : BaseViewModel() {
    val errorsChannel = Channel<Throwable>()
    private val _progressBarFlow = MutableStateFlow(false)
    val progressBarFlow: StateFlow<Boolean>
        get() = _progressBarFlow

    fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.createUser(
                    email,
                    password,
                    name,
                    age,
                    gender,
                )
            }.onSuccess {
                router.goFromSignUpToInstructors()
                _progressBarFlow.value = false
            }.onFailure {
                errorsChannel.send(it)
                _progressBarFlow.value = false
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

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }
}
