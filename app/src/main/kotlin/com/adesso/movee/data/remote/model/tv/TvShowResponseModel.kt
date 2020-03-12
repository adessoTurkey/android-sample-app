package com.adesso.movee.data.remote.model.tv

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.internal.util.Image
import com.adesso.movee.uimodel.TvShowGenreUiModel
import com.adesso.movee.uimodel.TvShowUiModel
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

    fun toUiModel(genres: List<TvShowGenreUiModel>) = TvShowUiModel(
        id = id,
        title = title,
        overview = overview,
        genres = genres,
        posterPath = posterPath,
        backdropPath = backdropPath,
        popularity = popularity,
        average = average,
        releaseDate = releaseDate
    )
}
