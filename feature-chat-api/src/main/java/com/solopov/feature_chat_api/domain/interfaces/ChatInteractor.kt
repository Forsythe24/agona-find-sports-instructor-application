package com.solopov.feature_chat_api.domain.interfaces

import kotlinx.coroutines.CoroutineDispatcher

class ChatInteractor(
    private val chatRepository: ChatRepository,
    private val dispatcher: CoroutineDispatcher
) {
}
