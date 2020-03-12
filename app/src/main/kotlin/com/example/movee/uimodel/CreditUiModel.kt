package com.example.movee.uimodel

import java.io.Serializable

interface CreditUiModel : Serializable {

    val creditId: String

    val id: Long

    val name: String

    val profilePath: String?
}