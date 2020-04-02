package com.adesso.movee.data.remote.model.user

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.uimodel.UserDetailUiModel
import com.squareup.moshi.Json

data class UserDetailResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "username") val username: String
) : BaseResponseModel() {

    fun toUiModel() = UserDetailUiModel(
        id = id,
        name = name,
        username = username
    )
}
