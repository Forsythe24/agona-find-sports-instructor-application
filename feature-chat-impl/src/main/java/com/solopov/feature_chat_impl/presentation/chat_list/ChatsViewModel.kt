package com.solopov.feature_chat_impl.presentation.chat_list

import androidx.lifecycle.viewModelScope
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.getMessage
import com.solopov.common.model.ChatCommon
import com.solopov.common.utils.DateFormatter
import com.solopov.feature_chat_api.domain.usecase.GetCurrentUserUseCase
import com.solopov.feature_chat_api.domain.usecase.LoadAllUserChatsUseCase
import com.solopov.feature_chat_impl.ChatRouter
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class ChatsViewModel(
    private val loadAllUserChatsUseCase: LoadAllUserChatsUseCase,
    private val chatMappers: ChatMappers,
    private val router: ChatRouter,
    private val resourceManager: ResourceManager,
    private val dateFormatter: DateFormatter,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : BaseViewModel() {
    private val _chatsState = MutableStateFlow<List<ChatItem>?>(null)
    val chatsState: StateFlow<List<ChatItem>?> = _chatsState.asStateFlow()

    private val _userState= MutableStateFlow<ChatItem?>(null)
    val userState: StateFlow<ChatItem?> = _userState.asStateFlow()


    init {
        setUser()
    }


    fun getAllChatsByUserId(userId: String) {
        setLoadingState(true)
        viewModelScope.launch {
            runCatching {
                loadAllUserChatsUseCase(userId)
            }.onSuccess {
                _chatsState.value = it.map(chatMappers::mapChatToChatItem)
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }.also {
                setLoadingState(false)
            }
        }

    }

    fun addDates(chats: List<ChatItem>): List<ChatItem> {

        chats.map { chatItem ->
            val date = dateFormatter.parseStringToDateTime(chatItem.lastMessageDate!!)!!
            val dateString = dateFormatter.formatDate(date)
            val now = Date()
            val todayDateString = dateFormatter.formatDate(now)

            if (dateString == todayDateString) {
                chatItem.userFriendlyLastMessageDate =
                    dateFormatter.formatDateTime(date).split(" ")[1]
            }

            val c1 = Calendar.getInstance()
            c1.add(Calendar.DAY_OF_YEAR, -1)

            val c2 = Calendar.getInstance()
            c2.time = date;

            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
            ) {
                chatItem.userFriendlyLastMessageDate =
                    resourceManager.getString(com.solopov.feature_chat_impl.R.string.yesterday)
            }
        }
        return chats
    }

    private fun setUser() {
        viewModelScope.launch {
            runCatching {
                getCurrentUserUseCase()
            }.onSuccess {
                _userState.value = chatMappers.mapUserToChatItem(it)
            }.onFailure {
                showMessage(it.getMessage(resourceManager))
            }
        }
    }

    fun openChat(chat: ChatCommon) {
        router.goFromChatsToChat(chat)
    }

    companion object {
        const val MESSAGE_SYNC_INTERVAL = 5000L
    }
}
