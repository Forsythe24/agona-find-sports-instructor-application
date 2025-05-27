package com.solopov.feature_chat_impl.presentation.chat

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.getMessage
import com.solopov.common.model.ChatCommon
import com.solopov.common.utils.DateFormatter
import com.solopov.feature_chat_api.domain.usecase.GetCurrentUserUseCase
import com.solopov.feature_chat_api.domain.usecase.LoadChatMessagesUseCase
import com.solopov.feature_chat_api.domain.usecase.SendMessageUseCase
import com.solopov.feature_chat_impl.ChatRouter
import com.solopov.feature_chat_impl.R
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.data.mappers.MessageMappers
import com.solopov.feature_chat_impl.data.network.StompManager
import com.solopov.feature_chat_impl.presentation.chat.model.MessageItem
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val chatMappers: ChatMappers,
    private val messageMappers: MessageMappers,
    private val router: ChatRouter,
    private val dateFormatter: DateFormatter,
    private val resManager: ResourceManager,
    private val stompManager: StompManager,
    private val resourceManager: ResourceManager,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val loadChatMessagesUseCase: LoadChatMessagesUseCase,
) : BaseViewModel() {

    private val _chatState = MutableStateFlow<List<MessageItem>?>(null)
    val chatState: StateFlow<List<MessageItem>?> = _chatState.asStateFlow()

    private val _receiverState = MutableStateFlow<ChatItem?>(null)
    val receiverState: StateFlow<ChatItem?> = _receiverState.asStateFlow()

    private val _senderState = MutableStateFlow<ChatItem?>(null)
    val senderState: StateFlow<ChatItem?> = _senderState.asStateFlow()

    var isMessageListeningStarted = false

    fun setReceiver(chat: ChatCommon) {
        _receiverState.value = chatMappers.mapChatCommonToChatItem(chat)
    }

    fun createNewMessage(userId: String, message: MessageItem) {
        viewModelScope.launch {
            runCatching {
                sendMessageUseCase(userId, messageMappers.mapMessageItemToMessage(message))
                sendMessage(message)
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    fun downloadMessages(chatId: String) {
        viewModelScope.launch {
            runCatching {
                loadChatMessagesUseCase(chatId)
            }.onSuccess {
                _chatState.value = it.map(messageMappers::mapMessageToMessageItem)
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    fun setSender() {
        viewModelScope.launch {
            runCatching {
                getCurrentUserUseCase()
            }.onSuccess {
                _senderState.value = chatMappers.mapUserToChatItem(it)
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }
        }
    }


    fun formatDateTime(date: Date): String {
        return dateFormatter.formatDateTime(date)
    }

    fun parseStringToDate(dateString: String): Date? {
        return dateFormatter.parseStringToDate(dateString)
    }

    fun openUserProfile(userId: String) {
        router.openUserProfile(userId)
    }

    fun goToEventCalendar(partnerName: String) {
        router.goFromChatToEventCalendar(partnerName)
    }

    fun goBack() {
        router.goBackToChats()
    }

    fun initMessageReceiving(chatId: String) {
        if (isMessageListeningStarted) return
        isMessageListeningStarted = true

        viewModelScope.launch {
            stompManager.connectAndSubscribe(chatId, ::onMessageReceived, ::onError)
        }
    }

    private fun onMessageReceived(message: MessageItem) {
        updateMessages(message)
    }

    private fun onError(throwable: Throwable) {
        viewModelScope.launch {
            showMessage(resourceManager.getString(R.string.unknown_error_message))
        }
    }

    private fun sendMessage(message: MessageItem) {
        stompManager.sendMessage(message.chatId, message)
    }

    private fun updateMessages(message: MessageItem) {
        val updatedMessages = _chatState.value?.toMutableList() ?: mutableListOf()
        updatedMessages.add(message)
        _chatState.value = updatedMessages
    }

    override fun onCleared() {
        stompManager.disconnect()
        super.onCleared()
    }

    fun addDates(messages: List<MessageItem>): List<MessageItem> {
        if (messages.isEmpty()) {
            return messages
        }
        val dateIndices = mutableListOf(0)
        var offset = 1
        for (i in 0 until messages.size - 1) {
            val currMessageDate =
                dateFormatter.formatDate(dateFormatter.parseStringToDate(messages[i].date)!!)
            val nextMessageDate =
                dateFormatter.formatDate(dateFormatter.parseStringToDate(messages[i + 1].date)!!)
            if (currMessageDate != nextMessageDate) {
                dateIndices.add(i + 1 + offset)
                offset++
            }
        }
        val mutableMessages = messages.toMutableList()

        val todayString = dateFormatter.formatDateTo_ddMMMyyyy_DateFormat(Date())

        dateIndices.forEach { index ->
            with(mutableMessages[index]) {

                var chatDate = dateFormatter.formatDateTo_ddMMMyyyy_DateFormat(
                    dateFormatter.parseStringToDate(date)!!
                )

                if (todayString == chatDate) {
                    chatDate = resManager.getString(R.string.today)
                }

                mutableMessages.add(
                    index,
                    MessageItem(id = null, chatId = "", text = "", senderId = "", date = chatDate)
                )
            }
        }

        return mutableMessages
    }
}
