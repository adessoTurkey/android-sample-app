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
        return try {
            dateFormat.parse(value)
        } catch (e: ParseException) {
            null
        }
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        return dateFormat.format(date)
    }

    companion object {
        private const val DATE_FORMAT_SERVER = "yyyy-MM-dd"
    }
}
