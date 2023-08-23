package com.adesso.movee.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.adesso.movee.data.local.database.entity.PopularMovieEntity
import com.adesso.movee.data.local.database.entity.PopularMovieWithGenres
import com.adesso.movee.internal.util.Failure
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.get
import com.github.michaelbull.result.onFailure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class PopularMovieRemoteMediator(
    private val fetchPopularMovies: suspend (page: Int, clearLocalData: Boolean)
    -> Result<List<PopularMovieEntity>, Failure>,
    private val getLastPageInLocal: suspend () -> Int?,
) : RemoteMediator<Int, PopularMovieWithGenres>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularMovieWithGenres>
    ): MediatorResult {
        val data = when (loadType) {
            LoadType.REFRESH -> {
                fetchPopularMovies.invoke(1, true).onFailure {
                    return MediatorResult.Error(it)
                }
            }

            LoadType.PREPEND ->
                return MediatorResult.Success(endOfPaginationReached = true)

            LoadType.APPEND -> {
                val lastPageNumber = withContext(Dispatchers.IO) {
                    getLastPageInLocal.invoke()
                }

                if (lastPageNumber == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                } else {
                    fetchPopularMovies.invoke(lastPageNumber + 1, false)
                        .onFailure {
                            return MediatorResult.Error(it)
                        }
                }
            }
        }

        return if (data.get().isNullOrEmpty()) {
            MediatorResult.Success(endOfPaginationReached = true)
        } else {
            MediatorResult.Success(endOfPaginationReached = false)
        }
    }
}
