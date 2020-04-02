package com.adesso.movee.data.remote.api

import com.adesso.movee.data.remote.model.user.UserDetailResponseModel
import com.adesso.movee.internal.util.api.HEADER_REQUIRE_SESSION_TOKEN
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserService {

    @GET(DETAIL)
    @Headers(HEADER_REQUIRE_SESSION_TOKEN)
    suspend fun fetchUserDetails(): UserDetailResponseModel

    companion object {
        const val DETAIL = "account"
    }
}
