package com.adesso.movee.internal.util

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.ToJson
import com.squareup.moshi.JsonWriter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class DateAdapter : JsonAdapter<Date>() {
    private val dateFormat = SimpleDateFormat(DATE_FORMAT_SERVER, Constant.Locale.LOCALE_ENGLISH)

    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            dateFormat.parse(dateAsString)
        } catch (e: ParseException) {
            null
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            writer.value(value.toString())
        }
    }

    companion object {
        private const val DATE_FORMAT_SERVER = "yyyy-MM-dd"
    }
}