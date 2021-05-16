package ru.ivadimn.flowroom.converters

import androidx.room.TypeConverter
import ru.ivadimn.flowroom.model.Sex

class SexConverter {
    @TypeConverter
    fun convertSexToString(sex : Sex) : String = sex.toString()

    @TypeConverter
    fun convertStringToSex(string : String) : Sex =  Sex.valueOf(string)

}