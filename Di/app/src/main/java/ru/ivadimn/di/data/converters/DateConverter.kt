package ru.ivadimn.di.data.converters

import androidx.room.TypeConverter
import org.threeten.bp.Instant


class DateConverter {
    @TypeConverter
    fun convertInstantToString(instant : Instant) : String {
        return instant.toString()
    }

    @TypeConverter
    fun convertStringToInstant(stringInstant : String) : Instant {
        return Instant.parse(stringInstant)
    }
}