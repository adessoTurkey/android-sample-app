package com.adesso.movee.domain

import com.adesso.movee.data.repository.LoginRepository
import com.adesso.movee.internal.util.UseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) : UseCase<Unit, LoginUseCase.Params>() {

    override suspend fun buildUseCase(params: Params) = repository.login(params)

    data class Params(val username: String, val password: String)
}
