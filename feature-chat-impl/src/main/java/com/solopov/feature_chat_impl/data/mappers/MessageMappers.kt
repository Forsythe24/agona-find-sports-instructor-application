package com.solopov.feature_chat_impl.data.mappers

import com.solopov.common.data.remote.model.MessageRemote
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_impl.presentation.chat.model.MessageItem
import javax.inject.Inject

class MessageMappers @Inject constructor() {
    fun mapMessageToMessageRemote(message: Message): MessageRemote {
        return with(message) {
            MessageRemote(
                id = id,
                chatId = chatId,
                text = text,
                senderId = senderId,
                date = date
            )
        }
    }

    fun mapMessageRemoteToMessage(messageRemote: MessageRemote): Message {
        return with(messageRemote) {
            Message(
                id = id,
                chatId = chatId,
                text = text,
                senderId = senderId,
                date = date
            )
        }
    }

    fun mapMessageRemoteToMessageItem(messageRemote: MessageRemote): MessageItem {
        return with(messageRemote) {
            MessageItem(
                id = id,
                chatId = chatId,
                text = text,
                senderId = senderId,
                date = date
            )
        }
    }

    fun mapMessageItemToMessageRemote(messageItem: MessageItem): MessageRemote {
        return with(messageItem) {
            MessageRemote(
                id = id,
                chatId = chatId,
                text = text,
                senderId = senderId,
                date = date
            )
        }
    }

    fun mapMessageItemToMessage(messageItem: MessageItem): Message {
        return with(messageItem) {
            Message(
                id = id,
                chatId = chatId,
                text = text,
                senderId = senderId,
                date = date
            )
        }
    }

    fun mapMessageToMessageItem(message: Message): MessageItem {
        return with(message) {
            MessageItem(
                id = id,
                chatId = chatId,
                text = text,
                senderId = senderId,
                date = date
            )
        }
    }
}
