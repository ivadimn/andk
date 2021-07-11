package ru.ivadimn.di.interfaces

import ru.ivadimn.di.data.entities.User

interface UserRepository {

    suspend fun selectAll() : List<User>
    suspend fun remove(userId: Long)

}