package com.adesso.movee.internal.util

import java.util.Locale as JavaLocale

object Constant {

    object Date {
        const val DATE_FORMAT_SHOW = "dd.MM.yyyy"
    }

    object Locale {
        val LOCALE_TURKISH = JavaLocale.forLanguageTag("tr-TR")
        val LOCALE_ENGLISH = JavaLocale.ENGLISH
    }
}