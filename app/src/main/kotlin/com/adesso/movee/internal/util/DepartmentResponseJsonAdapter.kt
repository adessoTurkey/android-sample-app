package com.adesso.movee.internal.util

import com.adesso.movee.data.remote.model.person.DepartmentResponseModel
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class DepartmentResponseJsonAdapter {

    @FromJson
    fun fromJson(department: String): DepartmentResponseModel {
        return when (department.toLowerCase(Constant.Locale.LOCALE_ENGLISH)) {
            ACTING -> DepartmentResponseModel.ACTING
            WRITING -> DepartmentResponseModel.WRITING
            DIRECTING -> DepartmentResponseModel.DIRECTING
            else -> DepartmentResponseModel.CREW
        }
    }

    @ToJson
    fun toJson(department: DepartmentResponseModel): String {
        return department.name
    }

    companion object {
        private const val ACTING = "acting"
        private const val WRITING = "writing"
        private const val DIRECTING = "directing"
    }
}
