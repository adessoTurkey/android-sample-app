package com.adesso.movee.internal.util.typeconverter

import androidx.room.TypeConverter

class GenreConverter {

    @TypeConverter
    fun fromString(genreIds: String): List<Long> {
        return genreIds.split(",").map { it.toLong() }
    }

    @TypeConverter
    fun listToString(list: List<Long>): String {
        return list.joinToString(separator = ",")
    }
}
