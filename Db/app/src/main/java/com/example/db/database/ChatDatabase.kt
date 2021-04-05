package com.example.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.db.database.converters.InstantConverter
import com.example.db.database.model.user.User

@Database(entities = [User::class], version = ChatDatabase.DB_VERSION)
@TypeConverters(InstantConverter::class)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "chat_db"

    }
}
