package com.adesso.movee.data.remote.model.tv

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.internal.util.Image
import com.adesso.movee.uimodel.TvShowCrewUiModel
import com.squareup.moshi.Json

data class TvShowCrewResponseModel(
    @Json(name = "credit_id") val creditId: String,
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "department") val department: String,
    @Json(name = "job") val job: String,
    @Json(name = "profile_path") @Image val profilePath: String?
) : BaseResponseModel() {

    fun toUiModel() = TvShowCrewUiModel(
        creditId = creditId,
        id = id,
        name = name,
        department = department,
        job = job,
        profilePath = profilePath
    )
}