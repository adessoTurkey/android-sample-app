package com.adesso.movee.uimodel

import android.content.Context
import com.adesso.movee.R
import com.adesso.movee.internal.extension.formatDate
import java.io.Serializable
import java.util.Date

data class PersonDetailUiModel(
    val id: Long,
    val name: String,
    val profilePath: String?,
    val biography: String,
    val placeOfBirth: String?,
    val birthday: Date?
) : Serializable {

    val dateString: String get() = birthday?.formatDate(DATE_FORMAT_BIRTH_DAY) ?: ""

    fun bornInfo(context: Context): String {
        return when {
            birthday != null && placeOfBirth.isNullOrBlank().not() -> {
                context.getString(R.string.born_date_and_place, dateString, placeOfBirth)
            }
            birthday != null -> {
                context.getString(R.string.born_date, dateString)
            }
            placeOfBirth.isNullOrBlank().not() -> {
                context.getString(R.string.born_place, placeOfBirth)
            }
            else -> ""
        }
    }

    companion object {
        const val DATE_FORMAT_BIRTH_DAY = "MMMM dd, YYYY"
    }
}