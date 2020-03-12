package com.example.movee.internal.extension

import com.example.movee.internal.util.Constant
import java.text.SimpleDateFormat
import java.util.Date

fun Date.formatDate(format: String): String {
    val dateFormat = SimpleDateFormat(format, Constant.Locale.default)
    return dateFormat.format(this)
}