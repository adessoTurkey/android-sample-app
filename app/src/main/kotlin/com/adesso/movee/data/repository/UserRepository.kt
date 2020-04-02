package com.adesso.movee.data.repository

import com.adesso.movee.data.remote.datasource.UserRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
) {

    suspend fun fetchUserDetails() = remoteDataSource.fetchUserDetails().toUiModel()
}
