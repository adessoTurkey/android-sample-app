package com.adesso.movee.uimodel

import com.adesso.movee.base.ListAdapterItem
import java.io.Serializable

interface GenreUiModel : ListAdapterItem, Serializable {

    override val id: Long

    val name: String
}
