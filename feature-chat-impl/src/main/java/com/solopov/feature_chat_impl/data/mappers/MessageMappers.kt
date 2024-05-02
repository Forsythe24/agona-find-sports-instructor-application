package com.solopov.feature_chat_impl.data.mappers

import com.solopov.common.data.firebase.model.MessageFirebase
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_impl.presentation.chat.model.MessageItem
import javax.inject.Inject

class MessageMappers @Inject constructor() {
    fun mapMessageToMessageFirebase(message: Message): MessageFirebase {
        return with(message) {
            MessageFirebase(
                id = id ?: "",
                text = text,
                senderId = senderId,
                date = date
            )
        }
    }

    fun mapMessageFirebaseToMessage(messageFirebase: MessageFirebase): Message {
        return with(messageFirebase) {
            Message(
                id = id,
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
                text = text,
                senderId = senderId,
                date = date
            )
        }
    }
}
