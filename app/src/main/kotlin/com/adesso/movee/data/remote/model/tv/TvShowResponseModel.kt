package com.adesso.movee.data.remote.model.tv

import com.adesso.movee.data.local.database.entity.NowPlayingTvShowEntity
import com.adesso.movee.data.local.database.entity.TopRatedTvShowEntity
import com.adesso.movee.data.local.database.entity.TvShowGenreCrossRefEntity
import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.internal.util.Image
import com.squareup.moshi.Json
import java.util.Date

data class TvShowResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "genre_ids") val genreIds: List<Long>,
    @Json(name = "poster_path") @Image val posterPath: String?,
    @Json(name = "backdrop_path") @Image val backdropPath: String?,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "vote_average") val average: Double,
    @Json(name = "first_air_date") val releaseDate: Date?
) : BaseResponseModel() {

    fun toNowPlayingEntity() = NowPlayingTvShowEntity(
        id = id,
        title = title,
        overview = overview,
        genreIds = genreIds,
        posterPath = posterPath,
        backdropPath = backdropPath,
        popularity = popularity,
        average = average,
        releaseDate = releaseDate
    )

    fun toTopRatedEntity() = TopRatedTvShowEntity(
        id = id,
        title = title,
        overview = overview,
        genreIds = genreIds,
        posterPath = posterPath,
        backdropPath = backdropPath,
        popularity = popularity,
        average = average,
        releaseDate = releaseDate
    )

    fun toTvShowGenreCrossRefEntity(genreId: Long) = TvShowGenreCrossRefEntity(
        id = id,
        genreId = genreId
    )
}
