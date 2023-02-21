package com.adesso.movee.internal.util

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

abstract class UseCase<out Type, in Params> where Type : Any {

    protected abstract suspend fun buildUseCase(params: Params): Type

    suspend fun run(params: Params): Result<Type, Failure> {
        return try {
            Ok(buildUseCase(params))
        } catch (failure: Failure) {
            Err(failure)
        }
    }

    object None {
        override fun toString() = "UseCase.None"
    }
}
