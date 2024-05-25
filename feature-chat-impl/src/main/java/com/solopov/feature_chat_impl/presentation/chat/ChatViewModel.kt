package com.solopov.feature_chat_impl.presentation.chat

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.model.ChatCommon
import com.solopov.common.utils.DateFormatter
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_chat_api.domain.interfaces.ChatInteractor
import com.solopov.feature_chat_impl.ChatRouter
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
import java.util.Date


class ChatViewModel(
    private val interactor: ChatInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val chatMappers: ChatMappers,
    private val messageMappers: MessageMappers,
    private val router: ChatRouter,
    private val dateFormatter: DateFormatter,
    private val resManager: ResourceManager,
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

    fun setReceiver(chat: ChatCommon) {
        _receiverFlow.value = chatMappers.mapChatCommonToChatItem(chat)
    }

    fun createNewMessage(userId: String, message: MessageItem) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.createNewMessage(userId, messageMappers.mapMessageItemToMessage(message))
            }.onSuccess {
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun downloadMessages(roomId: String) {

//        viewModelScope.launch {
//            runCatching(exceptionHandlerDelegate) {
//                interactor.downloadMessages(roomId)
//            }.onSuccess {
//                _chatFlow.value = it.map(messageMappers::mapMessageToMessageItem)
//            }.onFailure {
//                errorsChannel.send(it)
//            }
//        }

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


    fun formatDateTime(date: Date): String {
        return dateFormatter.formatDateTime(date)
    }

    fun parseStringToDate(dateString: String): Date? {
        return dateFormatter.parseStringToDate(dateString)
    }

    fun addDates(messages: List<MessageItem>): List<MessageItem> {
        if (messages.isEmpty()) {
            return messages
        }
        // 0 by default because the first message in a chat always needs its' date above it
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
                    //change to "today" if the chat date and the current date match
                    chatDate =
                        resManager.getString(com.solopov.feature_chat_impl.R.string.today)
                }

                mutableMessages.add(
                    index,
                    MessageItem(
                        id = null,
                        chatId = "",
                        text = "",
                        senderId = "",
                        date = chatDate
                    )
                )
            }
        }

        return mutableMessages
    }

    fun openUserProfile(userId: String) {
        router.openUserProfile(userId)
    }

    fun goToEventCalendar(partnerName: String) {
        router.goToEventCalendar(partnerName)
    }

    fun goBack() {
        router.goBack()
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
