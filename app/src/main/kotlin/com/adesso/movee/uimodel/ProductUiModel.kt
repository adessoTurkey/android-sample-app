package com.adesso.movee.uimodel

sealed class ProductUiModel {
    abstract val id: Long
    abstract val name: String
}

data class TvShowProductUiModel(
    override val id: Long,
    override val name: String
) : ProductUiModel()

data class MovieProductUiModel(
    override val id: Long,
    override val name: String
) : ProductUiModel()
