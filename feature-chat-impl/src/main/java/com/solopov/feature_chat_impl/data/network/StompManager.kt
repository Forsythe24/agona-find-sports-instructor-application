package com.solopov.feature_chat_impl.data.network

import android.util.Log
import com.google.gson.Gson
import com.solopov.common.core.config.AppProperties
import com.solopov.common.data.network.model.MessageRemote
import com.solopov.feature_chat_impl.data.mappers.MessageMappers
import com.solopov.feature_chat_impl.presentation.chat.model.MessageItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import javax.inject.Inject

class StompManager @Inject constructor(
    private val appProperties: AppProperties,
    private val messageMappers: MessageMappers,
    private val gson: Gson,
) {
    private var stompClient: StompClient? = null
    private val compositeDisposable = CompositeDisposable()

    fun connectAndSubscribe(chatId: String, onMessageReceived: (MessageItem) -> Unit, onError: (Throwable) -> Unit) {
        stompClient = Stomp.over(
            Stomp.ConnectionProvider.OKHTTP,
            appProperties.getBaseUrl() + MESSAGE_WEBSOCKET_PATH
        )

        val topicSubscription = stompClient?.topic(DESTINATION_PATH_TEMPLATE.format(chatId))
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ topicMessage ->
                val message = gson.fromJson(topicMessage.payload, MessageRemote::class.java)
                if (message.chatId == chatId) {
                    onMessageReceived(messageMappers.mapMessageRemoteToMessageItem(message))
                }
            }, onError)

        val lifecycleSubscription = stompClient?.lifecycle()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { lifecycleEvent ->
                when (lifecycleEvent.type) {
                    LifecycleEvent.Type.OPENED -> Log.d(STOMP_TAG, "Connection opened")
                    LifecycleEvent.Type.ERROR -> Log.e(STOMP_TAG, "Error", lifecycleEvent.exception)
                    LifecycleEvent.Type.CLOSED -> Log.d(STOMP_TAG, "Connection closed")
                    LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT -> Log.d(STOMP_TAG, "Server heartbeat failed")
                }
            }

        topicSubscription?.let { compositeDisposable.add(it) }
        lifecycleSubscription?.let { compositeDisposable.add(it) }

        stompClient?.connect()
    }

    fun sendMessage(chatId: String, message: MessageItem) {
        stompClient?.send(
            DESTINATION_TEMPLATE.format(chatId),
            gson.toJson(messageMappers.mapMessageItemToMessageRemote(message))
        )
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                Log.d(STOMP_TAG, "Message sent")
            }, { error ->
                Log.e(STOMP_TAG, "Error sending message", error)
            })?.let { compositeDisposable.add(it) }
    }

    fun disconnect() {
        stompClient?.disconnect()
        compositeDisposable.clear()
    }

    companion object {
        const val DESTINATION_PATH_TEMPLATE = "/topic/chat/%s/messages"
        const val DESTINATION_TEMPLATE = "/api/chat/%s/add_message"
        const val MESSAGE_WEBSOCKET_PATH = "message-websocket/websocket"
        const val STOMP_TAG = "StompManager"
    }
}

