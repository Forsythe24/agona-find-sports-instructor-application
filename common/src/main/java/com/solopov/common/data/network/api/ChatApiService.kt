package com.solopov.common.data.network.api

import com.solopov.common.data.network.model.ChatRemote
import com.solopov.common.data.network.model.MessageRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatApiService {
    @POST("chat/create")
    suspend fun createChat(
        @Body chat: ChatRemote,
    ): Boolean

    @GET("chat/{id}")
    suspend fun getChatById(
        @Path("id") id: String,
    ): Response<ChatRemote>

    @POST("chat/add_message")
    suspend fun addMessage(
        @Body message: MessageRemote,
    ): MessageRemote

    @GET("chat/all")
    suspend fun getAllChatsByUserId(
    ): Response<List<ChatRemote>?>

    @GET("chat/{id}/messages")
    suspend fun getAllMessagesByChatId(
        @Path("id") id: String,
    ): Response<List<MessageRemote>>

    @GET("chat/{id}/last_message")
    suspend fun getLastMessageByChatId(
        @Path("id") id: String,
    ): Response<MessageRemote>
}
