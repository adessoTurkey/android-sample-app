package com.adesso.movee.data.repository

import com.adesso.movee.data.remote.datasource.PersonRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepository @Inject constructor(
    private val remoteDataSource: PersonRemoteDataSource
) {

    suspend fun fetchPersonDetail(id: Long) = remoteDataSource.fetchPersonDetail(id).toUiModel()
}