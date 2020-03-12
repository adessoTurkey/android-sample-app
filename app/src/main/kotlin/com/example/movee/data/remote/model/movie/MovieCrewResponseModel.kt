package com.example.movee.data.remote.model.movie

import com.example.movee.data.remote.BaseResponseModel
import com.example.movee.internal.util.Image
import com.example.movee.uimodel.MovieCrewUiModel
import com.squareup.moshi.Json

data class MovieCrewResponseModel(
    @Json(name = "credit_id") val creditId: String,
    @Json(name = "department") val department: String,
    @Json(name = "id") val id: Long,
    @Json(name = "job") val job: String,
    @Json(name = "name") val name: String,
    @Json(name = "profile_path") @Image val profilePath: String?
) : BaseResponseModel() {

    fun toUiModel() = MovieCrewUiModel(
        creditId = creditId,
        id = id,
        name = name,
        department = department,
        job = job,
        profilePath = profilePath
    )
}

