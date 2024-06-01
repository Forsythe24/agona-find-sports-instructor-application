package com.solopov.common.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.solopov.common.data.network.model.MessageRemote
import com.solopov.common.utils.Constants
import com.solopov.common.utils.ParamsKey
import kotlinx.coroutines.tasks.await

class MessagePagingSource : PagingSource<DataSnapshot, MessageRemote>() {
    override fun getRefreshKey(state: PagingState<DataSnapshot, MessageRemote>): DataSnapshot? =
        null

    override suspend fun load(params: LoadParams<DataSnapshot>): LoadResult<DataSnapshot, MessageRemote> =
        try {
            val queryMessages = FirebaseDatabase.getInstance().reference.child("chat")
                .child("HqmLsYR0YbQ2NlIIyF688pz7s5g2JK3EIt4BouOBWCQjmqOVwa0CbjW2").child("message")
                .orderByKey().limitToLast(
                Constants.PAGE_SIZE
            )

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
