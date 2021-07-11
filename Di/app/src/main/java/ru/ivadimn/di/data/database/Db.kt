package ru.ivadimn.di.data.database

import android.content.Context
import androidx.room.Room

object Db {
    lateinit var instance : UserDatabase
    private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(context,
            UserDatabase::class.java,
            UserDatabase.DB_NAME)
            .build()
    }
}