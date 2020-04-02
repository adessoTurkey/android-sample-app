package com.adesso.movee.internal.popup

import androidx.annotation.Keep
import com.adesso.movee.R

private const val WARNING_LOTTIE = "lottie/popup_warning_lottie.json"
private const val INFO_LOTTIE = "lottie/popup_inform_lottie.json"
private const val ERROR_LOTTIE = "lottie/popup_error_lottie.json"

@Keep
enum class PopUpType(val iconRes: String, val bgRes: Int) {
    INFO(INFO_LOTTIE, R.drawable.background_popup_info),
    WARNING(WARNING_LOTTIE, R.drawable.background_popup_warning),
    ERROR(ERROR_LOTTIE, R.drawable.background_popup_error_round)
}
