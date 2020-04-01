package com.adesso.movee.uimodel

data class UserDetailUiModel(
    val id: Long,
    val name: String,
    val username: String
) {

    fun getDisplayName() = if (name.isNotBlank()) name else username
}