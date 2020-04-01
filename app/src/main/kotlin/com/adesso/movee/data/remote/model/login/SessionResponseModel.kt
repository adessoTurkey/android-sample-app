package com.adesso.movee.data.remote.model.login

import com.adesso.movee.data.remote.BaseResponseModel
import com.squareup.moshi.Json

data class SessionResponseModel(
    @Json(name = "success") val success: Boolean,
    @Json(name = "session_id") val sessionId: String
) : BaseResponseModel()