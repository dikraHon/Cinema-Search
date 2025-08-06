package com.example.cinemasearch.data.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromGenresList(genres: List<String>?): String? {
        return genres?.joinToString(",") ?: ""
    }

    @TypeConverter
    fun toGenresList(data: String?): List<String>? {
        return if (data.isNullOrEmpty()) {
            emptyList()
        } else {
            data.split(",").map { it.trim() }
        }
    }
}