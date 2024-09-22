package com.solopov.feature_chat_impl.presentation.chat

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.google.gson.Gson
import com.solopov.common.base.BaseViewModel
import com.solopov.common.core.config.AppProperties
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.jwt.JwtManager
import com.solopov.common.data.network.model.MessageRemote
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
import com.solopov.feature_chat_impl.utils.Constants.MESSAGE_WEBSOCKET_PATH
import com.solopov.feature_chat_impl.utils.Constants.STOMP_TAG
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage
import java.util.Collections
import java.util.Date


class ChatViewModel(
    private val interactor: ChatInteractor,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val chatMappers: ChatMappers,
    private val messageMappers: MessageMappers,
    private val router: ChatRouter,
    private val dateFormatter: DateFormatter,
    private val resManager: ResourceManager,
    private val appProperties: AppProperties,
    private val jwtManager: JwtManager,
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

    private var stompClient: StompClient? = null
    private var compositeDisposable: CompositeDisposable? = null
    private val gson = Gson()
    var isMessageListeningStarted = false

    fun setReceiver(chat: ChatCommon) {
        _receiverFlow.value = chatMappers.mapChatCommonToChatItem(chat)
    }

    fun createNewMessage(userId: String, message: MessageItem) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.createNewMessage(userId, messageMappers.mapMessageItemToMessage(message))

                sendMessage(message)
            }.onSuccess {
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    fun downloadMessages(chatId: String) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                interactor.downloadMessages(chatId)
            }.onSuccess {
                _chatFlow.value = it.map(messageMappers::mapMessageToMessageItem)
            }.onFailure {
                println(it)
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

    fun openUserProfile(userId: String) {
        router.openUserProfile(userId)
    }

    fun goToEventCalendar(partnerName: String) {
        router.goFromChatToEventCalendar(partnerName)
    }

    fun goBack() {
        router.goBackToChats()
    }

    fun initStomp(chatId: String?) {
        viewModelScope.launch {
            val token = jwtManager.getAccessJwt()
            val headerMap = Collections.singletonMap("Authorization", "Bearer $token")
            stompClient = Stomp.over(
                Stomp.ConnectionProvider.OKHTTP,
                appProperties.getBaseUrl() + MESSAGE_WEBSOCKET_PATH,
                headerMap
            )

            resetSubscriptions()

            if (stompClient != null) {
                val topicSubscribe = stompClient!!.topic("/topic/chat/$chatId/messages")
                    .subscribeOn(Schedulers.io(), false)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ topicMessage: StompMessage ->

                        val message = gson.fromJson(topicMessage.payload, MessageRemote::class.java)
                        if (message.chatId == chatId) {
                            updateMessages(messageMappers.mapMessageRemoteToMessageItem(message))
                        }

                    },
                        {
                            Log.e(STOMP_TAG, "Error", it)
                        }
                    )

                val lifecycleSubscribe = stompClient!!.lifecycle()
                    .subscribeOn(Schedulers.io(), false)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { lifecycleEvent: LifecycleEvent ->
                        when (lifecycleEvent.type!!) {
                            LifecycleEvent.Type.OPENED -> Log.d(
                                STOMP_TAG,
                                "Stomp connection opened"
                            )

                            LifecycleEvent.Type.ERROR -> Log.e(
                                STOMP_TAG,
                                "Error",
                                lifecycleEvent.exception
                            )

                            LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT,
                            LifecycleEvent.Type.CLOSED -> {
                                Log.d(STOMP_TAG, "Stomp connection closed")
                            }
                        }
                    }

                compositeDisposable!!.add(lifecycleSubscribe)
                compositeDisposable!!.add(topicSubscribe)

                if (!stompClient!!.isConnected) {
                    stompClient!!.connect()
                }

            } else {
                Log.e(STOMP_TAG, "Stomp client is null")
            }

            isMessageListeningStarted = true
        }
    }

    private fun updateMessages(message: MessageItem) {
        val updatedMessages = _chatFlow.value?.toMutableList() ?: mutableListOf()
        updatedMessages.add(message)
        _chatFlow.value = updatedMessages
    }

    private fun sendMessage(message: MessageItem) {
        sendCompletable(
            stompClient!!.send(
                "/api/chat/${message.chatId}/add_message",
                gson.toJson(messageMappers.mapMessageItemToMessageRemote(message))
            )
        )
    }

    private fun resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable!!.dispose()
        }
        compositeDisposable = CompositeDisposable()
    }

    private fun sendCompletable(request: Completable) {
        compositeDisposable?.add(
            request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.i(STOMP_TAG, "Stomp send")
                    },
                    {
                        Log.e(STOMP_TAG, "Stomp error", it)
                    }
                )
        )
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
                    chatDate = resManager.getString(com.solopov.feature_chat_impl.R.string.today)
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

    override fun onCleared() {
        errorsChannel.close()
        stompClient?.disconnect()
        compositeDisposable?.dispose()
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
