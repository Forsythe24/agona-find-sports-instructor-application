package com.solopov.feature_chat_impl.presentation.chat

import com.solopov.common.base.BaseViewModel
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.feature_chat_api.domain.interfaces.ChatInteractor

class ChatViewModel(
    private val interactor: ChatInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate
): BaseViewModel() {
}
