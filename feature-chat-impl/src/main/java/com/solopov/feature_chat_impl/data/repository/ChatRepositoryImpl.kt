package com.solopov.feature_chat_impl.data.repository

import com.solopov.common.data.firebase.dao.ChatFirebaseDao
import com.solopov.common.data.firebase.dao.UserFirebaseDao
import com.solopov.feature_chat_api.domain.interfaces.ChatRepository
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.model.User
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.data.mappers.MessageMappers
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatFirebaseDao: ChatFirebaseDao,
    private val userFirebaseDao: UserFirebaseDao,
    private val messageMappers: MessageMappers,
    private val chatMappers: ChatMappers,
) : ChatRepository {
    override suspend fun createNewMessage(roomId: String, message: Message) {
        chatFirebaseDao.addMessageToDatabase(roomId, messageMappers.mapMessageToMessageFirebase(message))
    }

    override suspend fun getCurrentUser(): User {
        return chatMappers.mapUserFirebaseToUser(userFirebaseDao.getCurrentUser())
    }

    override suspend fun downloadMessages(roomId: String): List<Message> {
        return chatFirebaseDao.getAllMessagesByRoomId(roomId).map { message ->
            messageMappers.mapMessageFirebaseToMessage(message)
        }
    }

}
