package com.adesso.movee.uimodel

data class TvShowCreatorUiModel(
    override val id: Long,
    override val creditId: String,
    override val name: String,
    override val profilePath: String?
) : CreditUiModel