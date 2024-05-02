package com.solopov.feature_chat_impl.presentation.chat_list

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_chat_api.domain.interfaces.ChatInteractor
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatsViewModel(
    private val interactor: ChatInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val chatMappers: ChatMappers,
): BaseViewModel() {
    private val _chatsFlow = MutableStateFlow<List<ChatItem>?>(null)
    val chatsFlow: StateFlow<List<ChatItem>?>
        get() = _chatsFlow

    val errorsChannel = Channel<Throwable>()
    
    private val _userFlow = MutableStateFlow<ChatItem?>(null)
    val userFlow: StateFlow<ChatItem?>
        get() = _userFlow
    
    fun getAllChatsByUserId(userId: String) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getAllChatsByUserId(userId)
            }.onSuccess {
                _chatsFlow.value = it.map(chatMappers::mapChatToChatItem)
            }.onFailure {
                errorsChannel.send(it)
            }
        }

    }

    fun setUser() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getCurrentUser()
            }.onSuccess {
                _userFlow.value = chatMappers.mapUserToChatItem(it)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }
}
