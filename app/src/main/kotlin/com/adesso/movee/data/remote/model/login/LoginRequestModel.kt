package com.adesso.movee.data.remote.model.login

import com.adesso.movee.data.remote.BaseRequestModel
import com.squareup.moshi.Json

data class LoginRequestModel(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String,
    @Json(name = "request_token") val requestToken: String
) : BaseRequestModel()
