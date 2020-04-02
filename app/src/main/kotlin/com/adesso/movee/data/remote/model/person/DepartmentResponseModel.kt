package com.adesso.movee.data.remote.model.person

import com.adesso.movee.uimodel.DepartmentUiModel

enum class DepartmentResponseModel {
    ACTING,
    DIRECTING,
    WRITING,
    CREW;

    fun toUiModel(): DepartmentUiModel {
        return when (this) {
            ACTING -> DepartmentUiModel.ACTING
            DIRECTING -> DepartmentUiModel.DIRECTING
            WRITING -> DepartmentUiModel.WRITING
            CREW -> DepartmentUiModel.CREW
        }
    }
}
