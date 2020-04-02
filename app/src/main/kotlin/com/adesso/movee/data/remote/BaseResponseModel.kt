package com.adesso.movee.data.remote

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.Serializable

open class BaseResponseModel : Serializable {

    fun toJson(moshi: Moshi): String {
        val jsonAdapter: JsonAdapter<BaseResponseModel> = moshi.adapter(this.javaClass)

        return jsonAdapter.toJson(this)
    }
}
