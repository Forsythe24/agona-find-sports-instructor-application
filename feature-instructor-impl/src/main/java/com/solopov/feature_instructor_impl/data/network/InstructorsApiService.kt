package com.solopov.feature_instructor_impl.data.network

import com.solopov.common.data.network.model.UserRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InstructorsApiService {
    @GET("instructors/{id}")
    suspend fun getInstructorsBySportId(
        @Path("id") id: Int,
    ): Response<List<UserRemote>>
}
