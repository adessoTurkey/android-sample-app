package com.adesso.movee.data.remote.model.movie

import com.adesso.movee.data.local.database.entity.MovieGenreCrossRefEntity
import com.adesso.movee.data.local.database.entity.NowPlayingMovieEntity
import com.adesso.movee.data.local.database.entity.PopularMovieEntity
import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.internal.util.Image
import com.squareup.moshi.Json
import java.util.Date

data class MovieResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "genre_ids") val genreIds: List<Long>,
    @Json(name = "poster_path") @Image val posterPath: String?,
    @Json(name = "backdrop_path") @Image val backdropPath: String?,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "vote_average") val average: Double,
    @Json(name = "adult") val isAdult: Boolean,
    @Json(name = "release_date") val releaseDate: Date?
) : BaseResponseModel() {

    fun toPopularMovieEntity() = PopularMovieEntity(
        id = id,
        title = title,
        overview = overview,
        genreIds = genreIds,
        posterPath = posterPath,
        backdropPath = backdropPath,
        popularity = popularity,
        average = average,
        isAdult = isAdult,
        releaseDate = releaseDate
    )

    fun toNowPlayingMovieEntity() = NowPlayingMovieEntity(
        id = id,
        title = title,
        overview = overview,
        genreIds = genreIds,
        posterPath = posterPath,
        backdropPath = backdropPath,
        popularity = popularity,
        average = average,
        isAdult = isAdult,
        releaseDate = releaseDate
    )

    fun toMovieGenreCrossRefEntity(genreId: Long) = MovieGenreCrossRefEntity(
        id = id,
        genreId = genreId
    )
}
