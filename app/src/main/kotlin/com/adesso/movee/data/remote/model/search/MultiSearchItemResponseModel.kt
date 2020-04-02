package com.adesso.movee.data.remote.model.search

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.data.remote.model.person.DepartmentResponseModel
import com.adesso.movee.internal.util.Image
import com.adesso.movee.uimodel.MovieMultiSearchUiModel
import com.adesso.movee.uimodel.MultiSearchUiModel
import com.adesso.movee.uimodel.PersonMultiSearchUiModel
import com.adesso.movee.uimodel.TvShowMultiSearchUiModel
import com.squareup.moshi.Json

sealed class MultiSearchItemResponseModel : BaseResponseModel() {

    abstract fun toUiModel(): MultiSearchUiModel

    companion object {
        const val MEDIA_TYPE = "media_type"
        const val MEDIA_TYPE_MOVIE = "movie"
        const val MEDIA_TYPE_TV = "tv"
        const val MEDIA_TYPE_PERSON = "person"
    }
}

data class MovieMultiSearchResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val name: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") @Image val posterPath: String?
) : MultiSearchItemResponseModel() {

    override fun toUiModel() = MovieMultiSearchUiModel(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath
    )
}

data class TvShowMultiSearchResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") @Image val posterPath: String?
) : MultiSearchItemResponseModel() {

    override fun toUiModel() = TvShowMultiSearchUiModel(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath
    )
}

data class PersonMultiSearchResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "known_for_department") val department: DepartmentResponseModel,
    @Json(name = "known_for") val knownFor: List<ProductResponseModel>,
    @Json(name = "profile_path") @Image val profilePath: String?
) : MultiSearchItemResponseModel() {

    override fun toUiModel() = PersonMultiSearchUiModel(
        id = id,
        name = name,
        department = department.toUiModel(),
        profilePath = profilePath,
        knownFor = knownFor.map { it.toUiModel() }
    )
}
