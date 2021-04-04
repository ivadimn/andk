package com.example.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.db.database.model.User

@Database(entities = [User::class], version = ChatDatabase.DB_VERSION)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "chat_db"

    }
}
