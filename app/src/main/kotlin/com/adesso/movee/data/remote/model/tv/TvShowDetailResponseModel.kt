package com.adesso.movee.data.remote.model.tv

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.internal.util.Image
import com.adesso.movee.uimodel.TvShowDetailUiModel
import com.squareup.moshi.Json
import java.util.Date

data class TvShowDetailResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "genres") val genres: List<TvShowGenreItemResponseModel>,
    @Json(name = "number_of_seasons") val seasonCount: Int,
    @Json(name = "episode_run_time") val episodeRuntime: List<Int>,
    @Json(name = "created_by") val creators: List<TvShowCreatorResponseModel>,
    @Json(name = "poster_path") @Image val posterPath: String?,
    @Json(name = "backdrop_path") @Image val backdropPath: String?,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "vote_average") val average: Double,
    @Json(name = "first_air_date") val releaseDate: Date?
) : BaseResponseModel() {

    fun toUiModel() = TvShowDetailUiModel(
        id = id,
        title = title,
        overview = overview,
        genres = genres.map { it.toUiModel() },
        seasonCount = seasonCount,
        posterPath = posterPath,
        backdropPath = backdropPath,
        popularity = popularity,
        average = average,
        releaseDate = releaseDate,
        runtime = episodeRuntime.firstOrNull(),
        creators = creators.map { it.toUiModel() }
    )
}
