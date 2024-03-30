package com.solopov.feature_authentication_impl.presentation

import com.solopov.common.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignUpViewModel: BaseViewModel() {

    private val _currentInstructorsFlow = MutableStateFlow<Int>(2)
    val currentInstructorsFlow: StateFlow<Int>
        get() = _currentInstructorsFlow
}
