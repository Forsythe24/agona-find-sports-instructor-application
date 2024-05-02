package com.solopov.feature_chat_impl.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.solopov.common.data.firebase.dao.ChatFirebaseDao
import com.solopov.common.data.firebase.dao.UserFirebaseDao
import com.solopov.feature_chat_api.domain.interfaces.ChatRepository
import com.solopov.feature_chat_api.domain.model.Chat
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.model.User
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.data.mappers.MessageMappers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatFirebaseDao: ChatFirebaseDao,
    private val userFirebaseDao: UserFirebaseDao,
    private val messageMappers: MessageMappers,
    private val chatMappers: ChatMappers,
) : ChatRepository {
    override suspend fun createNewMessage(userId: String, roomId: String, message: Message) {
        chatFirebaseDao.addMessageToDatabase(
            userId, roomId, messageMappers.mapMessageToMessageFirebase(message)
        )
    }

    override suspend fun getCurrentUser(): User {
        return chatMappers.mapUserFirebaseToUser(userFirebaseDao.getCurrentUser())
    }

    override suspend fun downloadMessages(userId: String, roomId: String): List<Message> {
        return chatFirebaseDao.getAllMessagesByRoomId(userId, roomId).map { message ->
            messageMappers.mapMessageFirebaseToMessage(message)
        }
    }

    override suspend fun getAllChatsByUserId(userId: String): List<Chat> {
        var receiverId = ""
        val receiversIds = chatFirebaseDao.getAllReceiversByUserId(userId)

        val lastMessages = receiversIds.map { id ->
            receiverId = id
            chatFirebaseDao.getLastMessageByUsersIds(userId, receiverId)
        }

        val chats = receiversIds.map { id ->
            userFirebaseDao.getUserByUid(id)
        }.map(chatMappers::mapUserFirebaseToChat)

        for (i in chats.indices) {
            chats[i].lastMessageText = lastMessages[i].text
            chats[i].lastMessageDate= lastMessages[i].date
        }

        return chats
//        val users = receiversIds.map {id ->
//            userFirebaseDao.getUserByUid(id)
//        }
//        return chatFirebaseDao.getAllReceiversByUserId(userId)
//            .map { _receiverId ->
//                val receiverId = _receiverId
//                userFirebaseDao.getUserByUid(_receiverId)
//            }.map(chatMappers::mapUserFirebaseToChat)
//            .map { chat ->
//                println(receiverId)
//                val lastMessage = chatFirebaseDao.getLastMessageByUsersIds(userId, receiverId)
//                chat.lastMessageDate = lastMessage.date
//                chat.lastMessageText = lastMessage.text
//
//                chat
//            }
    }

    override suspend fun getRecentMessages(): Flow<PagingData<Message>> {
        val pager = Pager(
            config = PagingConfig(10),
            pagingSourceFactory = { chatFirebaseDao },
        ).flow.map { pagingData ->
            pagingData.map { messageFirebase ->
                messageMappers.mapMessageFirebaseToMessage(messageFirebase)
            }
        }
        return pager
    }


}
