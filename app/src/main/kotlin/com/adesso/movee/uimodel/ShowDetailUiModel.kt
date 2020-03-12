package com.adesso.movee.uimodel

import android.content.Context
import com.adesso.movee.R

interface ShowDetailUiModel : ShowUiModel {
    val runtime: Int?

    fun runtime(context: Context): String {
        return runtime?.let { runtime ->
            context.getString(R.string.min_formatted, runtime)
        } ?: context.getString(R.string.not_specified)
    }
}