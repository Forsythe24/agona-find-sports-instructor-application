package com.solopov.feature_instructor_impl.data.network

import com.solopov.feature_instructor_impl.data.network.model.InstructorRemote
import com.solopov.feature_instructor_impl.data.network.pojo.response.InstructorsResponse
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface InstructorApi {

    @GET("/players")
    suspend fun getInstructors(
        @Path(value = "id") id: Int
    ): InstructorsResponse?
}
