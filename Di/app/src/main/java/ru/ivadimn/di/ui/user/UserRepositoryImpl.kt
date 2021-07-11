package ru.ivadimn.di.ui.user

import android.util.Patterns
import org.threeten.bp.Instant
import ru.ivadimn.di.data.UserDao
import ru.ivadimn.di.data.database.Db
import ru.ivadimn.di.data.entities.User
import ru.ivadimn.di.exceptions.IncorrectFormException
import ru.ivadimn.di.interfaces.UserRepository
import java.util.regex.Pattern

class UserRepositoryImpl(
    private val dao : UserDao
) : UserRepository {

     override suspend fun selectAll() : List<User> {
        val count = dao.getCountUsers()
        if (count == 0) {
            insertUsersIfZero()
        }
        return dao.selectAll()
    }

    override suspend fun remove(userId: Long) {
        dao.deleteById(userId)
    }

    private suspend fun insertUsersIfZero() {
        val users = (0 .. 20).map {
            User(0, "Name$it", "Family$it", if (it < 10) "+7900123569$it" else  "+790012356$it",
                "name$it@mail.ru",  "",  Instant.now(), Instant.now())
        }
        dao.insert(users)
    }



}