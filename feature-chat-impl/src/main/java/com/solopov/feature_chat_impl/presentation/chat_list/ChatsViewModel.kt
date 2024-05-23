package com.solopov.feature_chat_impl.presentation.chat_list

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.model.ChatCommon
import com.solopov.common.utils.DateFormatter
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import com.solopov.feature_chat_api.domain.interfaces.ChatInteractor
import com.solopov.feature_chat_impl.ChatRouter
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class ChatsViewModel(
    private val interactor: ChatInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val chatMappers: ChatMappers,
    private val router: ChatRouter,
    private val resManager: ResourceManager,
    private val dateFormatter: DateFormatter
) : BaseViewModel() {
    private val _chatsFlow = MutableStateFlow<List<ChatItem>?>(null)
    val chatsFlow: StateFlow<List<ChatItem>?>
        get() = _chatsFlow

    val errorsChannel = Channel<Throwable>()

    private val _userFlow = MutableStateFlow<ChatItem?>(null)
    val userFlow: StateFlow<ChatItem?>
        get() = _userFlow


    private val _progressBarFlow = MutableStateFlow(false)
    val progressBarFlow: StateFlow<Boolean>
        get() = _progressBarFlow


    fun getAllChatsByUserId(userId: String) {
        _progressBarFlow.value = true
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.getAllChatsByUserId(userId)
            }.onSuccess {
                _chatsFlow.value = it.map(chatMappers::mapChatToChatItem)
                _progressBarFlow.value = false
            }.onFailure {
                errorsChannel.send(it)
                _progressBarFlow.value = false
            }
        }

    }

    fun date(chats: List<ChatItem>): List<ChatItem> {

        chats.map { chatItem ->
            val date = dateFormatter.parseStringToDateTime(chatItem.lastMessageDate!!)!!
            val dateString = dateFormatter.formatDate(date)
            val now = Date()
            val todayDateString = dateFormatter.formatDate(now)

            if (dateString == todayDateString) {
                chatItem.userFriendlyLastMessageDate =
                    dateFormatter.formatDateTime(date).split(" ")[1]
            }

            val c1 = Calendar.getInstance();
            c1.add(Calendar.DAY_OF_YEAR, -1); // yesterday

            val c2 = Calendar.getInstance();
            c2.time = date;

            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
            ) {
                chatItem.userFriendlyLastMessageDate =
                    resManager.getString(com.solopov.feature_chat_impl.R.string.yesterday)
            }
        }
        return chats
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

    fun openChat(chat: ChatCommon) {
        router.openChat(chat)
    }
}
