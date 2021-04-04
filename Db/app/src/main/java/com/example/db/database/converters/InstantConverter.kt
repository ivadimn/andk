package com.example.db.database.converters

import androidx.room.TypeConverter
import org.threeten.bp.Instant


class InstantConverter  {

    @TypeConverter
    fun convertInstantToString(instant : Instant) : String = instant.toString()
    @TypeConverter
    fun convertStringToInstant(instantString: String) : Instant =
        Instant.parse(instantString)
}