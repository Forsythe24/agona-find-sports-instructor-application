package com.solopov.feature_chat_impl.data.mappers

import com.solopov.common.data.firebase.model.UserFirebase
import com.solopov.common.model.ChatCommon
import com.solopov.feature_chat_api.domain.model.Chat
import com.solopov.feature_chat_api.domain.model.User
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem
import javax.inject.Inject

class ChatMappers @Inject constructor() {

    fun mapChatCommonToChatItem(chatCommon: ChatCommon): ChatItem {
        return with(chatCommon) {
            ChatItem(
                userId,
                name,
                photo,
                null,
                null,
                null
            )
        }
    }

    fun mapChatItemToChatCommon(chat: ChatItem): ChatCommon {
        return with(chat) {
            ChatCommon(
                userId,
                name,
                photo,
            )
        }
    }

    fun mapChatToChatItem(chat: Chat): ChatItem {
        return with(chat) {
            ChatItem(
                userId,
                name,
                photo,
                lastMessageDate,
                lastMessageText,
                lastMessageDate
            )
        }
    }

    fun mapUserToChatItem(user: User): ChatItem {
        return with(user) {
            ChatItem(
                id,
                name,
                photo,
                null,
                null,
                null
            )
        }
    }

    fun mapUserFirebaseToChat(userFirebase: UserFirebase): Chat {
        return with(userFirebase) {
            Chat(
                userFirebase.id,
                name,
                photo,
                null,
                null
            )
        }
    }

    fun mapUserFirebaseToUser(userFirebase: UserFirebase): User {
        return with(userFirebase) {
            User(
                userFirebase.id,
                email,
                password,
                name,
                age,
                gender,
                sport,
                photo,
                experience,
                description,
                rating,
                hourlyRate,
                isInstructor
            )
        }
    }
}
