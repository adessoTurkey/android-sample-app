package com.adesso.movee.data.remote.model.login

import com.adesso.movee.data.remote.BaseRequestModel
import com.squareup.moshi.Json

data class SessionRequestModel(
    @Json(name = "request_token") val requestToken: String
) : BaseRequestModel()
