package com.solopov.feature_chat_impl.data.repository

import com.solopov.common.data.network.ApiError
import com.solopov.common.data.network.api.ChatApiService
import com.solopov.common.data.network.api.UserApiService
import com.solopov.common.data.network.makeSafeApiCall
import com.solopov.common.data.network.model.ChatRemote
import com.solopov.common.data.network.utils.NetworkStateProvider
import com.solopov.feature_chat_api.domain.ChatRepository
import com.solopov.feature_chat_api.domain.model.Chat
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.model.User
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.data.mappers.MessageMappers
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val messageMappers: MessageMappers,
    private val chatMappers: ChatMappers,
    private val apiService: ChatApiService,
    private val userApiService: UserApiService,
    private val networkStateProvider: NetworkStateProvider,
) : ChatRepository {
    override suspend fun createNewMessage(userId: String, message: Message) {
        try {
            makeSafeApiCall(networkStateProvider) {
                apiService.getChatById(message.chatId)
            }
            apiService.addMessage(messageMappers.mapMessageToMessageRemote(message))
        } catch (ex: Exception) {
            if (ex is ApiError.NotFoundException) {
                apiService.createChat(ChatRemote(message.chatId, userId))
                apiService.addMessage(messageMappers.mapMessageToMessageRemote(message))
            } else {
                throw ex
            }
        }
    }

    override suspend fun getCurrentUser(): User {
        return chatMappers.mapUserRemoteToUser(makeSafeApiCall(networkStateProvider) {
            userApiService.getCurrentUser()
        })
    }

    override suspend fun downloadMessages(chatId: String): List<Message> {
        return makeSafeApiCall(networkStateProvider) {
            apiService.getAllMessagesByChatId(chatId)
        }.map(messageMappers::mapMessageRemoteToMessage)
    }

    override suspend fun getAllChatsByUserId(userId: String): List<Chat> {
        val receiversIds = (makeSafeApiCall(networkStateProvider) {
            apiService.getAllChatsByUserId()
        } ?: listOf()).map { chat ->
            val receiverId = if (chat.id.startsWith(userId)) {
                chat.id.removePrefix(userId)
            } else {
                chat.id.removeSuffix(userId)
            }
            receiverId
        }

        val allLastMessages = receiversIds.map { id ->
            makeSafeApiCall(networkStateProvider) {
                apiService.getLastMessageByChatId(userId + id)
            }
        }

        val chats = receiversIds.map { id ->
            makeSafeApiCall(networkStateProvider) {
                userApiService.getUser(id)
            }
        }.map(chatMappers::mapUserRemoteToChat)

        for (i in chats.indices) {
            chats[i].lastMessageText = allLastMessages[i].text
            chats[i].lastMessageDate = allLastMessages[i].date
        }

        return chats
    }
}
