package com.solopov.common.data.firebase.dao

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.firebase.exceptions.ChatDataRetrievingException
import com.solopov.common.data.firebase.exceptions.SavingMessageFailedException
import com.solopov.common.data.firebase.model.MessageFirebase
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.ParamsKey
import com.solopov.common.utils.runCatching
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatFirebaseDao @Inject constructor(
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val dbReference: DatabaseReference,
    private val resManager: ResourceManager,
): PagingSource<DataSnapshot, MessageFirebase>() {
    suspend fun addMessageToDatabase(
        userId: String,
        roomId: String,
        message: MessageFirebase,
    ) {
        runCatching(exceptionHandlerDelegate) {
            val messageDbRef = dbReference
                .child(resManager.getString(R.string.chat_lower))
                .child(userId)
                .child(roomId)
                .child(resManager.getString(R.string.message))

            message.id = messageDbRef.push().key ?: ""

            messageDbRef.child(message.id).setValue(message)
        }.onSuccess {
        }.onFailure {
            throw SavingMessageFailedException(resManager.getString(R.string.failed_save_message_exception))
        }
    }


    suspend fun getAllReceiversByUserId(id: String): List<String> {
        runCatching(exceptionHandlerDelegate) {

            dbReference
                .child(resManager.getString(R.string.chat_lower))
                .child(id)
                .get().await()

        }.onSuccess { dataSnapshot ->
            return dataSnapshot.children.map { child ->

                val roomId = child.key.toString()

                val receiverId = if (roomId.startsWith(id)) {
                    roomId.removePrefix(id)
                } else {
                    roomId.removeSuffix(id)
                }

                receiverId
            }
        }.onFailure {
            throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
        }
        throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
    }

    suspend fun getAllMessagesByRoomId(
        userId: String,
        roomId: String,
    ): List<MessageFirebase> {
        runCatching(exceptionHandlerDelegate) {

            dbReference
                .child(resManager.getString(R.string.chat_lower))
                .child(userId)
                .child(roomId)
                .child(resManager.getString(R.string.message))
                .get().await()
        }.onSuccess { dataSnapshot ->
            return dataSnapshot.children.map { child ->
                with(child) {
                    MessageFirebase(
                        child(resManager.getString(R.string.id)).value.toString(),
                        child(resManager.getString(R.string.text)).value.toString(),
                        child(resManager.getString(R.string.sender_id)).value.toString(),
                        child(resManager.getString(R.string.date)).value.toString(),
                    )
                }
            }
        }.onFailure {
            throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
        }
        throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
    }

    suspend fun getLastMessageByUsersIds(senderId: String, receiverId: String): MessageFirebase {
        runCatching(exceptionHandlerDelegate){
            dbReference
                .child(resManager.getString(R.string.chat_lower))
                .child(senderId)
                .child(senderId + receiverId)
                .child(resManager.getString(R.string.message))
                .orderByKey()
                .limitToLast(1)
                .get().await()
        }.onSuccess { dataSnapshot ->
            println(dataSnapshot)
            return with(dataSnapshot.children.last()) {
                MessageFirebase(
                    child(resManager.getString(R.string.id)).value.toString(),
                    child(resManager.getString(R.string.text)).value.toString(),
                    child(resManager.getString(R.string.sender_id)).value.toString(),
                    child(resManager.getString(R.string.date)).value.toString(),
                )
            }
        }.onFailure {
            throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
        }
        throw ChatDataRetrievingException(resManager.getString(R.string.chat_data_retrieving_failed_exception))
    }

    suspend fun updateMessage(roomId: String, message: MessageFirebase) {
        val messageDetails = HashMap<String, Any>()
        with(message) {
            messageDetails[resManager.getString(R.string.id)] = id
            messageDetails[resManager.getString(R.string.text)] = text
        }
        runCatching(exceptionHandlerDelegate) {

            dbReference.child(resManager.getString(R.string.chat_lower)).child(roomId)
                .child(resManager.getString(R.string.message)).child(message.id)
                .updateChildren(messageDetails).addOnCompleteListener { }.await()
        }.onSuccess {
            return
        }.onFailure {
            throw SavingMessageFailedException(resManager.getString(R.string.failed_save_message_exception))
        }
    }

    override fun getRefreshKey(state: PagingState<DataSnapshot, MessageFirebase>): DataSnapshot? = null

    override suspend fun load(params: LoadParams<DataSnapshot>): LoadResult<DataSnapshot, MessageFirebase> =
        try {


            val queryMessages = dbReference.child("chat").child("HqmLsYR0YbQ2NlIIyF688pz7s5g2JK3EIt4BouOBWCQjmqOVwa0CbjW2").child("message").orderByKey().limitToLast(ParamsKey.PAGE_SIZE)

            val currentPage = params.key ?: queryMessages.get().await()

            val lastVisibleMessageKey = currentPage.children.last().key
            val nextPage = queryMessages.startAfter(lastVisibleMessageKey).get().await()

            val messages = currentPage.children.map { snapshot ->
                with(snapshot) {
                    MessageFirebase(
                        child("id").value.toString(),
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
