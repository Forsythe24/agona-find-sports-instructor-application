package com.solopov.feature_instructor_impl.data.network

import com.solopov.feature_instructor_impl.data.network.pojo.response.InstructorData
import retrofit2.http.GET
import retrofit2.http.Path

interface InstructorApi {

    @GET("instructors/{id}")
    suspend fun getInstructorsBySportId(
        @Path("id") id: Int
    ): List<InstructorData>?
}
