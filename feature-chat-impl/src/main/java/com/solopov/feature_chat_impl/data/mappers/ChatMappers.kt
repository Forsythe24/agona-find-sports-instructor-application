package com.solopov.feature_chat_impl.data.mappers

import com.solopov.common.data.firebase.model.UserFirebase
import com.solopov.common.model.UserCommon
import com.solopov.feature_chat_api.domain.model.User
import com.solopov.feature_chat_impl.presentation.chat_list.model.ChatItem
import javax.inject.Inject

class ChatMappers @Inject constructor() {

    fun mapUserCommonToChatItem(userCommon: UserCommon): ChatItem {
        return with(userCommon) {
            ChatItem(
                name,
                id,
                photo,
            )
        }
    }

    fun mapUserToChatItem(user: User): ChatItem {
        return with(user) {
            ChatItem(
                name,
                id,
                photo,
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
