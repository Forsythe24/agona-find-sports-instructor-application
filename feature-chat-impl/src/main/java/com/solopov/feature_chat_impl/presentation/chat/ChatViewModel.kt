package com.solopov.feature_chat_impl.presentation.chat

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.model.UserCommon
import com.solopov.common.utils.DateFormatter
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_chat_api.domain.interfaces.ChatInteractor
import com.solopov.feature_chat_impl.R
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.data.mappers.MessageMappers
import com.solopov.feature_chat_impl.presentation.chat.model.MessageItem
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class ChatViewModel(
    private val interactor: ChatInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val chatMappers: ChatMappers,
    private val messageMappers: MessageMappers,
) : BaseViewModel() {

    private val _chatFlow = MutableStateFlow<List<MessageItem>?>(null)
    val chatFlow: StateFlow<List<MessageItem>?>
        get() = _chatFlow

    private var _messageFlow = MutableStateFlow<PagingData<MessageItem>?>(null)
    val messageFlow: StateFlow<PagingData<MessageItem>?>
        get() = _messageFlow

    private val _receiverFlow = MutableStateFlow<ChatItem?>(null)
    val receiverFlow: StateFlow<ChatItem?>
        get() = _receiverFlow

    private val _senderFlow = MutableStateFlow<ChatItem?>(null)
    val senderFlow: StateFlow<ChatItem?>
        get() = _senderFlow

    val errorsChannel = Channel<Throwable>()

    fun setReceiver(user: UserCommon) {
        _receiverFlow.value = chatMappers.mapUserCommonToChatItem(user)
    }

    fun createNewMessage(roomId: String, message: MessageItem) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.createNewMessage(roomId, messageMappers.mapMessageItemToMessage(message))
            }.onSuccess {

            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun downloadMessages(roomId: String) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.downloadMessages(roomId)
            }.onSuccess {
                _chatFlow.value = it.map(messageMappers::mapMessageToMessageItem)
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun getRecentMessages() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getRecentMessages()
            }.onSuccess {
                val list: MutableCollection<PagingData<MessageItem>> = mutableListOf()
                val collection = it.map { pagingData ->
                    pagingData.map { message ->
                        messageMappers.mapMessageToMessageItem(message)
                    }
                }.toCollection(list)

            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun setSender() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getCurrentUser()
            }.onSuccess {
                _senderFlow.value = chatMappers.mapUserToChatItem(it)
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

fun <T> Flow<T>.mutableStateIn(
    scope: CoroutineScope,
    initialValue: T
): MutableStateFlow<T> {
    val flow = MutableStateFlow(initialValue)

    scope.launch {
        this@mutableStateIn.collect(flow)
    }

    return flow
}
