package com.adesso.movee.uimodel

import com.adesso.movee.base.ListAdapterItem

data class TvShowCastUiModel(
    override val creditId: String,
    override val id: Long,
    override val name: String,
    override val profilePath: String?,
    val character: String
) : ListAdapterItem, CreditUiModel
