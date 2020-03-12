package com.adesso.movee.data.remote.model.tv

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.internal.util.Image
import com.adesso.movee.uimodel.TvShowCreatorUiModel
import com.squareup.moshi.Json

data class TvShowCreatorResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "credit_id") val creditId: String,
    @Json(name = "name") val name: String,
    @Json(name = "profile_path") @Image val profilePath: String?
) : BaseResponseModel() {

    fun toUiModel() = TvShowCreatorUiModel(
        id = id,
        creditId = creditId,
        name = name,
        profilePath = profilePath
    )
}