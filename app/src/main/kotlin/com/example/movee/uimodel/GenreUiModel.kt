package com.example.movee.uimodel

import com.example.movee.base.ListAdapterItem
import java.io.Serializable

interface GenreUiModel : ListAdapterItem, Serializable {

    override val id: Long

    val name: String
}