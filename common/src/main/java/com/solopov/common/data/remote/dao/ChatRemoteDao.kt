package com.solopov.common.data.remote.dao

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.remote.SportApi
import com.solopov.common.data.remote.exceptions.ChatDataRetrievingException
import com.solopov.common.data.remote.model.ChatRemote
import com.solopov.common.data.remote.model.MessageRemote
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.ParamsKey
import com.solopov.common.utils.runCatching
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRemoteDao @Inject constructor(
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val dbReference: DatabaseReference,
    private val resManager: ResourceManager,
    private val api: SportApi,
) : PagingSource<DataSnapshot, MessageRemote>() {
    suspend fun createMessage(
        userId: String,
        message: MessageRemote,
    ) {
        runCatching(exceptionHandlerDelegate) {
            api.getChatById(message.chatId)
        }.onSuccess {
            // if it's not the first message of this chat
            api.addMessage(message)
        }.onFailure {
            // if it's the first message of this chat (chat hasn't been created yet)
            withContext(Dispatchers.IO) {
                api.createChat(ChatRemote(message.chatId, userId))
                api.addMessage(message)
            }
        }

    }

//    private suspend fun createChat(
//        userId: String,
//        roomId: String,
//    ) = withContext(Dispatchers.IO) {
//        api.createChat(ChatRemote(roomId, userId))
//    }
//
//    private suspend fun addMessage(
//        message: MessageRemote
//    ): MessageRemote = withContext(Dispatchers.IO) {
//        api.addMessage(message)
//    }

    suspend fun getAllReceiversByUserId(id: String): List<String> {
        runCatching(exceptionHandlerDelegate) {
            api.getAllChatsByUserId()
        }.onSuccess { chats ->
            return if (chats.isNullOrEmpty()) {
                listOf()
            } else {
                chats.map { chat ->

                    val receiverId = if (chat.id.startsWith(id)) {
                        chat.id.removePrefix(id)
                    } else {
                        chat.id.removeSuffix(id)
                    }

                    receiverId
                }
            }
        }.onFailure {
            throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
        }
        throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
    }

    suspend fun getAllMessagesByChatId(
        chatId: String,
    ): List<MessageRemote> {
        runCatching(exceptionHandlerDelegate) {
            api.getAllMessagesByChatId(chatId);
        }.onSuccess {
            return it
        }.onFailure {
            throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
        }
        throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
    }

    suspend fun getLastMessageByChatId(chatId: String): MessageRemote {
        runCatching(exceptionHandlerDelegate) {
            api.getLastMessageByChatId(chatId)
        }.onSuccess {
            return it
        }.onFailure {
            throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
        }
        throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
    }

    override fun getRefreshKey(state: PagingState<DataSnapshot, MessageRemote>): DataSnapshot? =
        null

    override suspend fun load(params: LoadParams<DataSnapshot>): LoadResult<DataSnapshot, MessageRemote> =
        try {


            val queryMessages = dbReference.child("chat")
                .child("HqmLsYR0YbQ2NlIIyF688pz7s5g2JK3EIt4BouOBWCQjmqOVwa0CbjW2").child("message")
                .orderByKey().limitToLast(ParamsKey.PAGE_SIZE)

            val currentPage = params.key ?: queryMessages.get().await()

            val lastVisibleMessageKey = currentPage.children.last().key
            val nextPage = queryMessages.startAfter(lastVisibleMessageKey).get().await()

            val messages = currentPage.children.map { snapshot ->
                with(snapshot) {
                    MessageRemote(
                        0,
                        "",
                        child("text").value.toString(),
                        child("senderId").value.toString(),
                        child("date").value.toString(),
                    )
                }
            }

            LoadResult.Page(
                data = messages,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            throw e
        }


}
