package com.adesso.movee.data.remote.model.search

import com.adesso.movee.data.remote.BaseResponseModel
import com.squareup.moshi.Json

class MultiSearchResponseModel(
    @Json(name = "results") val multiSearchResults: List<MultiSearchItemResponseModel>
) : BaseResponseModel()
