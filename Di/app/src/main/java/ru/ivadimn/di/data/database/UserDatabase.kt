package ru.ivadimn.di.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.ivadimn.di.data.UserDao
import ru.ivadimn.di.data.converters.DateConverter
import ru.ivadimn.di.data.entities.User

@Database(entities = [
    User::class
    ] , version = UserDatabase.DB_VERSION)
@TypeConverters(DateConverter::class)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao

    companion object {
        const val DB_NAME = "user_database"
        const val DB_VERSION = 1
    }
}