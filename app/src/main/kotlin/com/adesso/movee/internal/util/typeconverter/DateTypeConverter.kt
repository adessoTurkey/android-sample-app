package com.adesso.movee.internal.util.typeconverter

import androidx.room.TypeConverter
import com.adesso.movee.internal.util.Constant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class DateTypeConverter {
    private val dateFormat = SimpleDateFormat(DATE_FORMAT_SERVER, Constant.Locale.LOCALE_ENGLISH)

    @TypeConverter
    fun fromString(value: String?): Date? {
        return value?.let {
            try {
                dateFormat.parse(it)
            } catch (e: ParseException) {
                null
            }
        }
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        return date?.let { dateFormat.format(it) }
    }

    companion object {
        private const val DATE_FORMAT_SERVER = "yyyy-MM-dd"
    }
}
