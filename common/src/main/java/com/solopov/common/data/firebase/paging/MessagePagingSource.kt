package com.solopov.common.data.firebase.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.solopov.common.data.firebase.model.MessageFirebase
import com.solopov.common.utils.ParamsKey
import com.solopov.common.utils.runCatching
import kotlinx.coroutines.tasks.await

class MessagePagingSource: PagingSource<DataSnapshot, MessageFirebase>() {
    override fun getRefreshKey(state: PagingState<DataSnapshot, MessageFirebase>): DataSnapshot? = null

    override suspend fun load(params: LoadParams<DataSnapshot>): LoadResult<DataSnapshot, MessageFirebase> =
        try {
            val queryMessages = FirebaseDatabase.getInstance().reference.child("chat").child("HqmLsYR0YbQ2NlIIyF688pz7s5g2JK3EIt4BouOBWCQjmqOVwa0CbjW2").child("message").orderByKey().limitToLast(
                ParamsKey.PAGE_SIZE)

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
