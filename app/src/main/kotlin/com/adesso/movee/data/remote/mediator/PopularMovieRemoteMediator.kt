package com.adesso.movee.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.adesso.movee.data.local.database.entity.PopularMovieEntity
import com.adesso.movee.data.local.database.entity.PopularMovieWithGenres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PopularMovieRemoteMediator(
    private val fetchPopularMovies: suspend (page: Int) -> List<PopularMovieEntity>,
    private val getLastPageInLocal: suspend () -> Int?,
    private val clearLocalData: suspend () -> Unit,
) : RemoteMediator<Int, PopularMovieWithGenres>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularMovieWithGenres>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    withContext(Dispatchers.IO) { clearLocalData.invoke() }
                    1
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
                        lastPageNumber + 1
                    }
                }
            }

            if (fetchPopularMovies.invoke(page).isEmpty()) {
                MediatorResult.Success(endOfPaginationReached = true)
            } else {
                MediatorResult.Success(endOfPaginationReached = false)
            }
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
