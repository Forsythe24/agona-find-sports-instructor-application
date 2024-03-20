package com.solopov.feature_instructor_impl.data.network.pojo.response

import com.google.gson.annotations.SerializedName

class InstructorsResponse {
    @SerializedName("data")
    val instructorsList: List<InstructorData>? = null
}

class InstructorData {
    val id: Int? = null
    @SerializedName("sport_id")
    val sportId: Int? = null
    val name: String? = null
    val photo: String? = null
    val age: Int? = null
    val rating: Float? = null
    val otherData: OtherData? = null
}

class OtherData {
    @SerializedName("main_team")
    val genderData: GenderData? = null
}

class GenderData {
    val gender: String? = null
}

