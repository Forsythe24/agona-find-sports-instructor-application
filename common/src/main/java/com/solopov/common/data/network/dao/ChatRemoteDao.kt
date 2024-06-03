package com.solopov.common.data.network.dao

import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.network.SportApi
import com.solopov.common.data.network.exceptions.ChatDataRetrievingException
import com.solopov.common.data.network.exceptions.HttpException
import com.solopov.common.data.network.model.ChatRemote
import com.solopov.common.data.network.model.MessageRemote
import javax.inject.Inject

class ChatRemoteDao @Inject constructor(
    private val resManager: ResourceManager,
    private val api: SportApi,
) {

    suspend fun createMessage(
        userId: String,
        message: MessageRemote,
    ) {

        // trying to retrieve the chat
        val response = api.getChatById(message.chatId)

        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
            404 -> {
                // if it's the first message of this chat (chat hasn't been created yet)
                api.createChat(ChatRemote(message.chatId, userId))
                api.addMessage(message)
            }

            else -> {
                // if it's not the first message of this chat
                api.addMessage(message)
            }
        }

    }

    suspend fun getAllReceiversByUserId(id: String): List<String> {

        val response = api.getAllChatsByUserId()

        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))

            200 -> {
                return if (response.body() == null) {
                    listOf()
                } else {
                    response.body()!!.map { chat ->

                        val receiverId = if (chat.id.startsWith(id)) {
                            chat.id.removePrefix(id)
                        } else {
                            chat.id.removeSuffix(id)
                        }

                        receiverId
                    }
                }
            }

            else -> throw ChatDataRetrievingException(resManager.getString(R.string.failed_to_retrieve_chat_data))
        }
    }

    suspend fun getAllMessagesByChatId(
        chatId: String,
    ): List<MessageRemote> {
        val response = api.getAllMessagesByChatId(chatId)

        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
            200 -> return response.body()!!
            else -> throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
        }
    }

    suspend fun getLastMessageByChatId(chatId: String): MessageRemote {

        val response = api.getLastMessageByChatId(chatId)

        when (response.code()) {
            500 -> throw HttpException.InternalServerErrorException(resManager.getString(R.string.internal_server_error_exception))
            503 -> throw HttpException.ServiceUnavailableException(resManager.getString(R.string.service_unavailable_exception))
            200 -> return response.body()!!
            else -> throw ChatDataRetrievingException(resManager.getString(R.string.failed_to_retrieve_chat_data))
        }
    }
}
