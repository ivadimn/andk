package ru.ivadimn.flowroom.presentation

import kotlinx.coroutines.flow.Flow
import ru.ivadimn.flowroom.db.Db
import ru.ivadimn.flowroom.model.Sex
import ru.ivadimn.flowroom.model.User

class UserRepository {

    private val userDao = Db.instance.userDao()

    suspend fun insert(name : String, age : Int, sex : Sex) {
        userDao.insert(listOf(User(0, name, age, sex)))
    }

    suspend fun selectAll() : List<User> {
        return userDao.selectAll()
    }

    suspend fun selectAllFlow() : Flow<List<User>> {
        return userDao.selectAllFlowDistinctUntilChanged()
    }
}