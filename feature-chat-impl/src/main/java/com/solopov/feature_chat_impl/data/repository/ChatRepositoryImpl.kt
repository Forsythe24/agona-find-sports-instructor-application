package com.solopov.feature_chat_impl.data.repository

import com.solopov.common.data.network.api.UserApiService
import com.solopov.common.data.network.exceptions.ChatDataRetrievingException
import com.solopov.common.data.network.exceptions.HttpException
import com.solopov.common.data.network.exceptions.UserException
import com.solopov.common.data.network.model.ChatRemote
import com.solopov.common.data.network.utils.HttpValues
import com.solopov.common.data.network.utils.handleApiErrors
import com.solopov.common.data.network.utils.makeSafeApiCall
import com.solopov.feature_chat_api.domain.interfaces.ChatRepository
import com.solopov.feature_chat_api.domain.model.Chat
import com.solopov.feature_chat_api.domain.model.Message
import com.solopov.feature_chat_api.domain.model.User
import com.solopov.feature_chat_impl.data.exception.MessageCreationFailedException
import com.solopov.feature_chat_impl.data.mappers.ChatMappers
import com.solopov.feature_chat_impl.data.mappers.MessageMappers
import com.solopov.common.data.network.api.ChatApiService
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection

class ChatRepositoryImpl @Inject constructor(
    private val messageMappers: MessageMappers,
    private val chatMappers: ChatMappers,
    private val apiService: ChatApiService,
    private val userApiService: UserApiService,
) : ChatRepository {
    override suspend fun createNewMessage(userId: String, message: Message) {
        val response = apiService.getChatById(message.chatId)
        when (response.code()) {
            in HttpValues.serverErrorRange -> throw HttpException.ServerFailedException("The server encountered an unexpected condition")
            HttpURLConnection.HTTP_NOT_FOUND -> {
                apiService.createChat(ChatRemote(message.chatId, userId))
                apiService.addMessage(messageMappers.mapMessageToMessageRemote(message))
            }

            in HttpValues.clientErrorRange -> throw HttpException.ClientException()
            HttpsURLConnection.HTTP_OK -> {
                apiService.addMessage(messageMappers.mapMessageToMessageRemote(message))
            }

            else -> throw MessageCreationFailedException("Failed to create new message")
        }
    }

    override suspend fun getCurrentUser(): User {
        val result = makeSafeApiCall {
            userApiService.getCurrentUser()
        }
        return chatMappers.mapUserRemoteToUser(
            result.handleApiErrors(
                hashMapOf(
                    HttpURLConnection.HTTP_UNAUTHORIZED to HttpException.UnauthorizedException(
                        "Failed to download current user data due to lack of authority",
                    ),
                    HttpURLConnection.HTTP_NOT_FOUND to UserException.UserNotFoundException("Current user was not found")
                )
            )
        )
    }

    override suspend fun downloadMessages(chatId: String): List<Message> {
        val result = makeSafeApiCall {
            apiService.getAllMessagesByChatId(chatId)
        }
        return result.handleApiErrors(
            hashMapOf(
                HttpURLConnection.HTTP_UNAUTHORIZED to HttpException.UnauthorizedException(
                    "Failed to download messages by chat id ($chatId) due to lack of authority",
                )
            )
        ).map(messageMappers::mapMessageRemoteToMessage)
    }

    override suspend fun getAllChatsByUserId(userId: String): List<Chat> {
        val response = apiService.getAllChatsByUserId()

        val receiversIds = when (response.code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> throw HttpException.UnauthorizedException("Failed to fetch user ($userId) chat list due to lack of authority")
            in HttpValues.serverErrorRange -> throw HttpException.ServerFailedException("The server encountered an unexpected condition")
            in HttpValues.clientErrorRange -> throw HttpException.ClientException()
            HttpsURLConnection.HTTP_OK -> {
                if (response.body() == null) {
                    listOf()
                } else {
                    response.body()!!.map { chat ->
                        val receiverId = if (chat.id.startsWith(userId)) {
                            chat.id.removePrefix(userId)
                        } else {
                            chat.id.removeSuffix(userId)
                        }

                        receiverId
                    }
                }
            }

            else -> throw ChatDataRetrievingException("Failed to retrieve chat data")
        }
        val allLastMessages = receiversIds.map { id ->
            makeSafeApiCall {
                apiService.getLastMessageByChatId(userId + id)
            }.handleApiErrors()
        }

        val chats = receiversIds.map { id ->
            makeSafeApiCall {
                userApiService.getUser(id)
            }.handleApiErrors(
                hashMapOf(
                    HttpURLConnection.HTTP_NOT_FOUND to UserException.UserNotFoundException("User $id was not found")
                )
            )
        }.map(chatMappers::mapUserRemoteToChat)

        for (i in chats.indices) {
            chats[i].lastMessageText = allLastMessages[i].text
            chats[i].lastMessageDate = allLastMessages[i].date
        }

        return chats
    }
}
