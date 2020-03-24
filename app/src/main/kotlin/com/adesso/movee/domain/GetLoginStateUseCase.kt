package com.adesso.movee.domain

import com.adesso.movee.data.repository.LoginRepository
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.LoginState
import javax.inject.Inject

class GetLoginStateUseCase @Inject constructor(
    private val repository: LoginRepository
) : UseCase<LoginState, UseCase.None>() {

    override suspend fun buildUseCase(params: None) = repository.getLoginState()
}