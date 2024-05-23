package com.solopov.common.data.remote.dao

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.database.DataSnapshot
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.remote.SportApi
import com.solopov.common.data.remote.exceptions.ChatDataRetrievingException
import com.solopov.common.data.remote.exceptions.HttpException
import com.solopov.common.data.remote.model.ChatRemote
import com.solopov.common.data.remote.model.MessageRemote
import com.solopov.common.utils.ExceptionHandlerDelegate
import javax.inject.Inject

class ChatRemoteDao @Inject constructor(
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val resManager: ResourceManager,
    private val api: SportApi,
) : PagingSource<DataSnapshot, MessageRemote>() {
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

    override fun getRefreshKey(state: PagingState<DataSnapshot, MessageRemote>): DataSnapshot? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<DataSnapshot>): LoadResult<DataSnapshot, MessageRemote> {
        TODO("Not yet implemented")
    }

//    override fun getRefreshKey(state: PagingState<DataSnapshot, MessageRemote>): DataSnapshot? =
//        null

//    override suspend fun load(params: LoadParams<DataSnapshot>): LoadResult<DataSnapshot, MessageRemote> =
//        try {
//
//
//            val queryMessages = dbReference.child("chat")
//                .child("HqmLsYR0YbQ2NlIIyF688pz7s5g2JK3EIt4BouOBWCQjmqOVwa0CbjW2").child("message")
//                .orderByKey().limitToLast(ParamsKey.PAGE_SIZE)
//
//            val currentPage = params.key ?: queryMessages.get().await()
//
//            val lastVisibleMessageKey = currentPage.children.last().key
//            val nextPage = queryMessages.startAfter(lastVisibleMessageKey).get().await()
//
//            val messages = currentPage.children.map { snapshot ->
//                with(snapshot) {
//                    MessageRemote(
//                        0,
//                        "",
//                        child("text").value.toString(),
//                        child("senderId").value.toString(),
//                        child("date").value.toString(),
//                    )
//                }
//            }
//
//            LoadResult.Page(
//                data = messages,
//                prevKey = null,
//                nextKey = nextPage
//            )
//        } catch (e: Exception) {
//            throw e
//        }


}
