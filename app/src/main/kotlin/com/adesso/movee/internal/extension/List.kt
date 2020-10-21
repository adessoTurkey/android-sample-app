package com.adesso.movee.internal.extension

import com.adesso.movee.data.local.database.entity.MovieGenreEntity
import com.adesso.movee.data.local.database.entity.TvShowGenreEntity
import com.adesso.movee.uimodel.MovieGenreUiModel
import com.adesso.movee.uimodel.TvShowGenreUiModel

fun <T> List<T>?.thisOrEmptyList() = this ?: emptyList()

fun List<MovieGenreEntity>.convertMovieGenres(): List<MovieGenreUiModel> = this.map {
    MovieGenreUiModel(it.id, it.name)
}

fun List<TvShowGenreEntity>.convertTvShowGenres(): List<TvShowGenreUiModel> = this.map {
    TvShowGenreUiModel(it.id, it.name)
}
