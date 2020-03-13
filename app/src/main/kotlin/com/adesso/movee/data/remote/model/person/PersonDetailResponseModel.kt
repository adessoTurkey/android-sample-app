package com.adesso.movee.data.remote.model.person

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.internal.util.Image
import com.adesso.movee.uimodel.PersonDetailUiModel
import com.squareup.moshi.Json
import java.util.Date

data class PersonDetailResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "profile_path") @Image val profilePath: String?,
    @Json(name = "biography") val biography: String,
    @Json(name = "place_of_birth") val placeOfBirth: String?,
    @Json(name = "birthday") val birthday: Date?
) : BaseResponseModel() {

    fun toUiModel() = PersonDetailUiModel(
        id = id,
        name = name,
        profilePath = profilePath,
        biography = biography,
        placeOfBirth = placeOfBirth,
        birthday = birthday
    )
}