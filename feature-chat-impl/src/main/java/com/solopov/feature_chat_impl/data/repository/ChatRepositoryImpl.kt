package com.solopov.feature_chat_impl.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.solopov.common.data.network.dao.ChatRemoteDao
import com.solopov.common.data.network.dao.UserRemoteDao
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
    private val chatRemoteDao: ChatRemoteDao,
    private val userRemoteDao: UserRemoteDao,
    private val messageMappers: MessageMappers,
    private val chatMappers: ChatMappers,
) : ChatRepository {
    override suspend fun createNewMessage(userId: String, message: Message) {

        chatRemoteDao.createMessage(
            userId, messageMappers.mapMessageToMessageRemote(message)
        )
    }

    override suspend fun getCurrentUser(): User {
        return chatMappers.mapUserRemoteToUser(userRemoteDao.getCurrentUser())
    }

    override suspend fun downloadMessages(chatId: String): List<Message> {
        return chatRemoteDao.getAllMessagesByChatId(chatId).map { message ->
            messageMappers.mapMessageRemoteToMessage(message)
        }
    }

    override suspend fun getAllChatsByUserId(userId: String): List<Chat> {
        var receiverId = ""
        val receiversIds = chatRemoteDao.getAllReceiversByUserId(userId)

        val allLastMessages = receiversIds.map { id ->
            receiverId = id
            chatRemoteDao.getLastMessageByChatId(userId + receiverId)
        }

        val chats = receiversIds.map { id ->
            userRemoteDao.getUserByUid(id)
        }.map(chatMappers::mapUserRemoteToChat)

        for (i in chats.indices) {
            chats[i].lastMessageText = allLastMessages[i].text
            chats[i].lastMessageDate = allLastMessages[i].date
        }

        return chats
    }

    override suspend fun getRecentMessages(): Flow<PagingData<Message>> {
        val pager = Pager(
            config = PagingConfig(10),
            pagingSourceFactory = { chatRemoteDao },
        ).flow.map { pagingData ->
            pagingData.map { messageFirebase ->
                messageMappers.mapMessageRemoteToMessage(messageFirebase)
            }
        }
        return pager
    }


}
