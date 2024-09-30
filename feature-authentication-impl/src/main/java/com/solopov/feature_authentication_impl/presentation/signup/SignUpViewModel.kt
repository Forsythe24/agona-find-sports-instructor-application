package com.solopov.feature_authentication_impl.presentation.signup

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

class SignUpViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val router: AuthRouter,
    private val validator: UserDataValidator,
    private val resourceManager: ResourceManager,
) : BaseViewModel() {

    private val _errorMessageChannel = Channel<String>()
    val errorMessageChannel = _errorMessageChannel.receiveAsFlow()

    private val _progressBarFlow = MutableStateFlow(false)
    val progressBarFlow: StateFlow<Boolean>
        get() = _progressBarFlow

    private val _emailErrorTextFlow = MutableStateFlow<String?>(null)
    val emailErrorTextFlow: StateFlow<String?>
        get() = _emailErrorTextFlow

    fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching {
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
                when (it) {
                    is ApiError.ConflictException -> _emailErrorTextFlow.value = resourceManager.getString(R.string.email_already_in_use_message)
                    else -> _errorMessageChannel.send(it.getMessage(resourceManager))
                }
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
        _errorMessageChannel.close()
        super.onCleared()
    }
}
