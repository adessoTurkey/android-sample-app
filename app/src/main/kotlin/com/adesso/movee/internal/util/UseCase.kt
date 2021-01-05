package com.adesso.movee.internal.util

import com.adesso.movee.internal.util.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

abstract class UseCase<out Type, in Params> where Type : Any {

    protected abstract suspend fun buildUseCase(params: Params): Type

    private suspend fun run(params: Params): Either<Failure, Type> {
        return try {
            Either.Right(buildUseCase(params))
        } catch (failure: Failure) {
            Either.Left(failure)
        }
    }

    open operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        val backgroundJob = scope.async { run(params) }
        scope.launch { onResult(backgroundJob.await()) }
    }

    object None {
        override fun toString() = "UseCase.None"
    }
}
