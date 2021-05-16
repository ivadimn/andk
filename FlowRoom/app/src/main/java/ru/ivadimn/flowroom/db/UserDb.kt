package ru.ivadimn.flowroom.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.ivadimn.flowroom.daos.UserDao
import ru.ivadimn.flowroom.model.User

@Database(entities = [User::class], version = UserDb.DB_VERSION)
abstract class UserDb : RoomDatabase() {

    abstract fun userDao() : UserDao

    companion object {
        const val DB_NAME = "users_db"
        const val DB_VERSION = 1
    }
}