package com.example.db.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object Db {
    lateinit var instance : ChatDatabase
    private set

    fun init(context : Context) {
        instance = Room.databaseBuilder(
            context,
            ChatDatabase::class.java,
            ChatDatabase.DB_NAME
        ).build()
    }
}