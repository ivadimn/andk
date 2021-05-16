package ru.ivadimn.flowroom.db

import android.content.Context
import androidx.room.Room

object Db {

    lateinit var instance : UserDb
    private set

    fun init(context : Context) {
        instance = Room.databaseBuilder(
            context,
            UserDb::class.java,
            UserDb.DB_NAME
        ).build()
    }
}