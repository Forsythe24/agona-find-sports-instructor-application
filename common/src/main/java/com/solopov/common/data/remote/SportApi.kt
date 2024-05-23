package com.solopov.common.data.remote

import com.solopov.common.data.remote.model.ChatRemote
import com.solopov.common.data.remote.model.CredentialsRemote
import com.solopov.common.data.remote.model.MessageRemote
import com.solopov.common.data.remote.model.UserRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SportApi {

    @GET("user/{id}")
    suspend fun getUser(
        @Path("id") id: String
    ): Response<UserRemote>

    @GET("user/current")
    suspend fun getCurrentUser(
    ): Response<UserRemote>

    @POST("user/update")
    suspend fun updateUser(
        @Body user: UserRemote
    ): Response<Boolean>

    @POST("user/update_password")
    suspend fun updatePassword(
        @Body credentials: CredentialsRemote
    ): Response<Boolean>

    @POST("user/verify")
    suspend fun verifyCredentials(
        @Body credentials: CredentialsRemote
    ): Response<Boolean>

    @POST("user/verify")
    suspend fun u(
        @Body credentials: CredentialsRemote
    ): Boolean

    @GET("instructors/{id}")
    suspend fun getInstructorsBySportId(
        @Path("id") id: Int
    ): Response<List<UserRemote>>

    @POST("chat/create")
    suspend fun createChat(
        @Body chat: ChatRemote
    ): Boolean

    @GET("chat/{id}")
    suspend fun getChatById(
        @Path("id") id: String
    ): Response<ChatRemote>

    @POST("chat/add_message")
    suspend fun addMessage(
        @Body message: MessageRemote
    ): MessageRemote

    @GET("chat/all")
    suspend fun getAllChatsByUserId(
    ): Response<List<ChatRemote>?>

    @GET("chat/{id}/messages")
    suspend fun getAllMessagesByChatId(
        @Path("id") id: String
    ): Response<List<MessageRemote>>

    @GET("chat/{id}/last_message")
    suspend fun getLastMessageByChatId(
        @Path("id") id: String
    ): Response<MessageRemote>
}
