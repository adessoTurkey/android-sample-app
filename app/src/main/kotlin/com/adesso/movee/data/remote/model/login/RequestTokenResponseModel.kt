package com.adesso.movee.data.remote.model.login

import com.adesso.movee.data.remote.BaseResponseModel
import com.squareup.moshi.Json

data class RequestTokenResponseModel(
    @Json(name = "success") val success: Boolean,
    @Json(name = "request_token") val requestToken: String
) : BaseResponseModel()
