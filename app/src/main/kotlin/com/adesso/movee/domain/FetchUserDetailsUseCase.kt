package com.adesso.movee.domain

import com.adesso.movee.data.repository.UserRepository
import com.adesso.movee.internal.util.UseCase
import com.adesso.movee.uimodel.UserDetailUiModel
import javax.inject.Inject

class FetchUserDetailsUseCase @Inject constructor(
    private val repository: UserRepository
) : UseCase<UserDetailUiModel, UseCase.None>() {

    override suspend fun buildUseCase(params: None) = repository.fetchUserDetails()
}