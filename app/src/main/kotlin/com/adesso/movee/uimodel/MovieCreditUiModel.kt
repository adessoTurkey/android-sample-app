package com.adesso.movee.uimodel

import java.io.Serializable

data class MovieCreditUiModel(
    val cast: List<MovieCastUiModel>,
    val crew: List<MovieCrewUiModel>
) : Serializable {

    val directors: String get() = crewNamesByJob(job = JOB_DIRECTOR)
    val writers: String get() = crewNamesByJob(job = JOB_WRITER)
    val stars: String get() = cast.joinToString(separator = ", ") { it.name }

    private fun crewNamesByJob(job: String, separator: String = ", "): String {
        return crew
            .filter { it.job == job }
            .joinToString(separator = separator) { it.name }
    }

    companion object {
        private const val JOB_DIRECTOR = "Director"
        private const val JOB_WRITER = "Writer"
    }
}
