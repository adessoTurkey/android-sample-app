package com.adesso.movee.data.remote.model.tv

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.internal.util.Image
import com.adesso.movee.uimodel.TvShowCastUiModel
import com.squareup.moshi.Json

data class TvShowCastResponseModel(
    @Json(name = "credit_id") val creditId: String,
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "profile_path") @Image val profilePath: String?,
    @Json(name = "character") val character: String
) : BaseResponseModel() {

    fun toUiModel() = TvShowCastUiModel(
        creditId = creditId,
        id = id,
        name = name,
        profilePath = profilePath,
        character = character
    )
}
