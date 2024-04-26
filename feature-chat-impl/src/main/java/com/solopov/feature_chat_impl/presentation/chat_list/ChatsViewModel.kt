package com.solopov.feature_chat_impl.presentation.chat_list

import com.solopov.common.base.BaseViewModel
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.feature_chat_api.domain.interfaces.ChatInteractor

class ChatsViewModel(
    interactor: ChatInteractor,
    exceptionHandlerDelegate: ExceptionHandlerDelegate
): BaseViewModel() {
}
