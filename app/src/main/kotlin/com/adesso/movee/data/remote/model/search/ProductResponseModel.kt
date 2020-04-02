package com.adesso.movee.data.remote.model.search

import com.adesso.movee.data.remote.BaseResponseModel
import com.adesso.movee.uimodel.MovieProductUiModel
import com.adesso.movee.uimodel.ProductUiModel
import com.adesso.movee.uimodel.TvShowProductUiModel
import com.squareup.moshi.Json

sealed class ProductResponseModel : BaseResponseModel() {

    abstract fun toUiModel(): ProductUiModel

    companion object {
        const val MEDIA_TYPE = "media_type"
        const val MEDIA_TYPE_TV = "tv"
        const val MEDIA_TYPE_MOVIE = "movie"
    }
}

data class TvShowProductResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String
) : ProductResponseModel() {

    override fun toUiModel() = TvShowProductUiModel(
        id = id,
        name = name
    )
}

data class MovieProductResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val name: String
) : ProductResponseModel() {

    override fun toUiModel() = MovieProductUiModel(
        id = id,
        name = name
    )
}
