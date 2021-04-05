package com.example.db.database.model.user

import com.example.db.database.Db
import com.example.db.database.model.user.User

class UsersRepository {

    private val userDao = Db.instance.userDao()

    suspend fun getAllUsers() : List<User> {
        return userDao.getAllUsers()
    }

    suspend fun removeUser(user: User) {
        userDao.removeUserById(user.id)
    }







}