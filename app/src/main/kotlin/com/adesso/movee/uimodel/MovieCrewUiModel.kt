package com.adesso.movee.uimodel

data class MovieCrewUiModel(
    override val creditId: String,
    override val id: Long,
    override val name: String,
    override val profilePath: String?,
    val department: String,
    val job: String
) : CreditUiModel
