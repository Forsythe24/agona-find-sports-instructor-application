package com.solopov.feature_authentication_impl.presentation

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val interactor: AuthInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate
): BaseViewModel() {

    private val _currentInstructorsFlow = MutableStateFlow<Int>(2)
    val currentInstructorsFlow: StateFlow<Int>
        get() = _currentInstructorsFlow

    val errorsChannel = Channel<Throwable>()

    fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ) {
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

            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }
}
