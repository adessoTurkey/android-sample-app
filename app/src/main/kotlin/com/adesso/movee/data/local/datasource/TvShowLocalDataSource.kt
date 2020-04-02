package com.adesso.movee.data.local.datasource

import android.content.SharedPreferences
import com.adesso.movee.data.local.delegate.listPreference
import com.adesso.movee.data.local.model.TvShowGenreLocalModel
import com.squareup.moshi.Moshi
import javax.inject.Inject

class TvShowLocalDataSource @Inject constructor(
    sharedPreferences: SharedPreferences,
    moshi: Moshi
) {

    private var genres: List<TvShowGenreLocalModel>? by listPreference(
        moshi = moshi,
        preferences = sharedPreferences,
        preferenceName = PREF_GENRES
    )

    fun fetchGenres(): List<TvShowGenreLocalModel>? = genres

    fun insertGenres(genreList: List<TvShowGenreLocalModel>?) {
        genres = genreList
    }

    companion object {
        private const val PREF_GENRES = "tv_show_genres"
    }
}
