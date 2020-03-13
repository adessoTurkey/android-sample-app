package com.adesso.movee.uimodel

data class TvShowCrewUiModel(
    override val creditId: String,
    override val id: Long,
    override val name: String,
    override val profilePath: String?,
    val job: String,
    val department: String
) : CreditUiModel
